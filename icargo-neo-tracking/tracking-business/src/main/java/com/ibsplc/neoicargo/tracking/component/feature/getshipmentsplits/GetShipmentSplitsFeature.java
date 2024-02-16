package com.ibsplc.neoicargo.tracking.component.feature.getshipmentsplits;

import com.ibsplc.neoicargo.awb.component.AwbComponent;
import com.ibsplc.neoicargo.awb.dao.entity.ShipmentKey;
import com.ibsplc.neoicargo.awb.vo.AwbRequestVO;
import com.ibsplc.neoicargo.tracking.dao.TrackingDAO;
import com.ibsplc.neoicargo.tracking.mapper.TrackingEntityMapper;
import com.ibsplc.neoicargo.tracking.vo.ActualFlightDataVO;
import com.ibsplc.neoicargo.tracking.vo.DLVPiecesUsageVO;
import com.ibsplc.neoicargo.tracking.vo.FlightTimePostfixEnum;
import com.ibsplc.neoicargo.tracking.vo.MilestoneCodeEnum;
import com.ibsplc.neoicargo.tracking.vo.MilestoneEventUsageVO;
import com.ibsplc.neoicargo.tracking.vo.MilestoneNameEnum;
import com.ibsplc.neoicargo.tracking.vo.MilestonePlanUsageVO;
import com.ibsplc.neoicargo.tracking.vo.ShipmentMilestoneEventVO;
import com.ibsplc.neoicargo.tracking.vo.ShipmentMilestonePlanVO;
import com.ibsplc.neoicargo.tracking.vo.SplitAirportVO;
import com.ibsplc.neoicargo.tracking.vo.SplitDetailsItemVO;
import com.ibsplc.neoicargo.tracking.vo.SplitFlightVO;
import com.ibsplc.neoicargo.tracking.vo.SplitMilestoneStatus;
import com.ibsplc.neoicargo.tracking.vo.SplitPlanCombinationMatchVO;
import com.ibsplc.neoicargo.tracking.vo.SplitPlansCombinationVO;
import com.ibsplc.neoicargo.tracking.vo.SplitVO;
import com.ibsplc.neoicargo.tracking.vo.TransitStationVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.stereotype.Component;

import javax.ws.rs.NotFoundException;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.function.Predicate;
import java.util.function.ToIntFunction;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.ibsplc.neoicargo.tracking.exception.TrackingErrors.AWB_NOT_FOUND;

/**
 * This feature builds the list of Splits from list of MilestonePlans and MilestoneEvents
 * IN: List of MilestonePlans, MilestoneEvents
 * OUT: List of Splits
 *
 * Vocabulary
 * 1. Each split - is a Graph.
 * Appropriately list of splits - list of independent Graphs.
 * 2. Split Blocks  - Vertex of Graph; Airport.
 * Airport - {in: [Plan(code=ARR), ...], out: [Plan(code=DEP), ...] }
 * 2. Split Arrow - Edge of Graph; Flight.
 * Flight - {Plan(code=DEP), Plan(code=ARR)}
 *
 * Algorithm overview
 * 1. Build flights
 * 2. Build airports
 * 3. Build splits
 * 3.1. Create a new graph\split
 * 3.2. Take unused airport from origin
 * 3.3. Find all airports connected to it through the flights
 * 3.4. Add all found airports to the graph\split and repeat p.3.1
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class GetShipmentSplitsFeature {

    private final TrackingDAO trackingDAO;
    private final TrackingEntityMapper entityMapper;
    private final AwbComponent awbComponent;

    private final Random random = new Random();

    @SuppressWarnings("unchecked")
    public List<SplitVO> perform(AwbRequestVO awbRequestVO) {
        log.info("Perform Get Shipment Splits Feature!");
        final var shipmentType = "A";
        var shipmentKey =  new ShipmentKey(awbRequestVO.getShipmentPrefix(), awbRequestVO.getMasterDocumentNumber())
                .toString();

        //Find Plans and Events by AWB
        var awbPlans = entityMapper.constructShipmentMilestonePlansVO(
                trackingDAO.findPlansByShipmentKeys(List.of(shipmentKey))
        );
        var awbEvents = entityMapper.constructShipmentMilestoneEventsVO(
                trackingDAO.findEventsByShipmentKeysAndType(List.of(shipmentKey), shipmentType)
        );
        var awbVOs = awbComponent.getAwbList( Arrays.asList(awbRequestVO));
        if (awbVOs.isEmpty()) {
            log.info("Can't find awb for {}", awbRequestVO);
            throw new NotFoundException(AWB_NOT_FOUND.getErrorMessage());
        }
        final var originAirportCode = awbVOs.get(0).getOrigin();
        final var dstAirportCode = awbVOs.get(0).getDestination();
        final var awbPieces = awbVOs.get(0).getStatedPieces();

        //Filter DEP and ARR Plans
        var depPlans = awbPlans.stream()
                .filter(plan -> plan.getMilestoneCode().equals(MilestoneCodeEnum.DEP))
                .collect(Collectors.toList());
        var arrPlans = awbPlans.stream()
                .filter(plan -> plan.getMilestoneCode().equals(MilestoneCodeEnum.ARR))
                .collect(Collectors.toList());

        //1. Build flights
        var flights = buildFlights(depPlans, arrPlans);
         log.info("Build {} flights", flights.size());

        //2. Build airports
        var airports = buildAirports(awbPlans, originAirportCode, dstAirportCode);
        log.info("Build {} airports", airports.size());

        //3. Build splits
        return buildSplits(airports, flights,awbPlans, awbEvents, originAirportCode, dstAirportCode, awbPieces);
    }

    /**
     * Build list of Splits from Flights, Airports
     * and calculate status based on Events
     */
    private List<SplitVO> buildSplits(
            List<SplitAirportVO> airports,
            List<SplitFlightVO> flights,
            List<ShipmentMilestonePlanVO> awbPlans,
            List<ShipmentMilestoneEventVO> awbEvents,
            String originAirportCode,
            String dstAirportCode,
            Integer awbPieces) {
        /**
         * We should make sure each Event will be used only withing specific Airport and its sub-splits.
         * So we wrap each Plan with MilestoneEventUsageVO which keeps the Plan and 'usedByAirportId' flag
         */
        var eventsWithUsage = awbEvents.stream()
                .map(event -> new MilestoneEventUsageVO(event, null))
                .collect(Collectors.toList());

        /**
         * The events we can't match by any plan.
         * Could be used to determine 'Actual flight data'
         */
        var notMatchedEventsWithUsage = getNotMatchedEvents(awbPlans, awbEvents);

        var dlvPiecesUsage = new DLVPiecesUsageVO(awbEvents, awbPieces);

        //Group airports by serialNumber of ARR and DEP Plans they are contains.
        var airportsByArrSerialNumber = airports.stream()
                .flatMap(airport ->
                        airport.getArr().stream()
                                .collect(Collectors.toMap(ShipmentMilestonePlanVO::getSerialNumber, ignored -> airport))
                                .entrySet()
                                .stream()
                )
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        var airportsByDepSerialNumber = airports.stream()
                .flatMap(airport ->
                        airport.getDep().stream()
                                .collect(Collectors.toMap(ShipmentMilestonePlanVO::getSerialNumber, ignored -> airport))
                                .entrySet()
                                .stream()
                )
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        var plannedPieces = new HashMap<String, Integer>();

        /**
         * Combine unconnected Airports to build Splits:
         *
         * 1. Take first unused airport from origin
         * 2. Find all airports connected to it through the flights
         * 3. Convert all found airports to SplitDetailsItemVO and group together into graph\split.
         * In such way we build independent graph\split.
         *
         * 4. if there are left some unused airports from origin then repeat p.1 to build next graph\split
         */

        //To track the airports we're going to process in current iteration, and already processed (to avoid duplicates)
        var toProcessAirports = new LinkedHashSet<SplitAirportVO>();
        var processedAirports = new LinkedHashSet<SplitAirportVO>();

        //We'll push all built splits there
        var splits = new LinkedList<SplitVO>();

        //Do until we have unused airports from Origin
        var originAirport = findOriginNotProcessedAirport(airports, originAirportCode, processedAirports);
        do {
            originAirport.ifPresent(toProcessAirports::add);

            /**
             * Beginning from the Airport from origin, find all connected airports to it,
             * convert each to SplitItem and repeat algorithm for each found airports,
             * until we go through all connected together airports.
             */
            var splitDetails = new LinkedList<SplitDetailsItemVO>();
            while (!toProcessAirports.isEmpty()) {
                var airport = toProcessAirports.iterator().next();

                /**
                 * Convert Airport to the SplitDetailsItemVO,
                 * calculating its Status(Arrived, Departed etc) based on Events and all other params
                 */
                var splitDetailsItem = buildSplitDetailsItemModel(
                        airport.getItemId(),
                        airport.getAirportCode(),
                        airport.getArr(),
                        airport.getDep(),
                        eventsWithUsage,
                        notMatchedEventsWithUsage,
                        dlvPiecesUsage,
                        getAirportItemIdConnectedByDepSerialNumber(airport, flights, airportsByArrSerialNumber),
                        Pair.of(originAirportCode, dstAirportCode),
                        plannedPieces
                );
                splitDetails.add(splitDetailsItem);

                toProcessAirports.remove(airport);
                processedAirports.add(airport);

                /**
                 * Find airports connected to the current, and add all, which are not processed yet,
                 * to the toProcess list, to process each of them in the same way in the next iteration
                 */
                var connectedAirports = Stream.concat(
                        getAirportsConnectedByArr(airport, flights, airportsByDepSerialNumber).stream(),
                        getAirportsConnectedByDep(airport, flights, airportsByArrSerialNumber).stream()
                )
                        .collect(Collectors.toList());
                for (var connectedAirport : connectedAirports) {
                    if (!processedAirports.contains(connectedAirport)) {
                        toProcessAirports.add(connectedAirport);
                    }
                }
            }

            /**
             * We already found all Airports connected to appropriate Airport from origin,
             * converted them to SplitDetailsItemVO, and pushed to splitDetails list.
             * Then we calculate the Split summery there, like total pieces, transition stations etc.
             * and push the successfully built SplitVO to splits list
             */
            if (!splitDetails.isEmpty()) {
                /**
                 * Normalize splits:
                 * If airports from DST >1 , merge airports from DST into one
                 * If airports from Origin >1, divide split, to each have only 1 airports from Origin
                 */
                var normalizedSplitDetailsList = normalizedSplit(
                        splitDetails,
                        originAirportCode,
                        dstAirportCode,
                        plannedPieces,
                        dlvPiecesUsage
                );

                normalizedSplitDetailsList.forEach(normalizedSplitDetails -> {
                    var split = new SplitVO();
                    split.setSplitDetails(normalizedSplitDetails);
                    split.setSplitNumber(splits.size() + 1);
                    split.setPieces(calculateSplitPieces(normalizedSplitDetails, originAirportCode));
                    split.setMilestoneStatus(determineMilestoneStatus(normalizedSplitDetails));
                    split.setTransitStations(buildTransitionStations(normalizedSplitDetails, originAirportCode, dstAirportCode));

                    splits.add(split);
                });
            }

            originAirport = findOriginNotProcessedAirport(airports, originAirportCode, processedAirports);
        } while (originAirport.isPresent());

        return splits;
    }

    private List<MilestoneEventUsageVO> getNotMatchedEvents(List<ShipmentMilestonePlanVO> awbPlans,
                                                            List<ShipmentMilestoneEventVO> awbEvents) {
        var eventsWithUsage = awbEvents.stream()
                .map(event -> new MilestoneEventUsageVO(event, null))
                .collect(Collectors.toList());
        awbPlans.forEach(plan -> findEventByPlan(plan.getSerialNumber().toString(), eventsWithUsage, plan));

        return eventsWithUsage.stream()
                .filter(evWithUsage -> evWithUsage.getUsedByAirportItemId() == null)
                .collect(Collectors.toList());
    }

    /**
     * Normalization rules:
     * 1. Split can have only ONE outgoing flight from origin. If there are more than one,
     * we divide such split into few.
     *
     * 2. Split can have only ONE Destination airport. If there are more than one,
     * we merge them into one.
     */
    private List<List<SplitDetailsItemVO>> normalizedSplit(
            List<SplitDetailsItemVO> splitDetails,
            String originAirportCode,
            String dstAirportCode,
            Map<String, Integer> plannedPieces,
            DLVPiecesUsageVO dlvPiecesUsage) {
        return divideSplitByOriginIfNeeded(splitDetails, originAirportCode, plannedPieces, dlvPiecesUsage).stream()
                .map(splitDetailsItems -> {
                    splitDetailsItems.stream()
                            .filter(item -> item.getOriginAirportCode().equals(originAirportCode))
                            .findFirst()
                            .ifPresent(originItem -> {
                                splitDetailsItems.remove(originItem);
                                splitDetailsItems.add(0, originItem);
                            });
                    return splitDetailsItems;
                })
                /**
                 * According to algorithm in result split we always have only one airport from origin and
                 * correctly built transfer airports, but we could have several airports from destination,
                 * which appropriately we have to merge into one.
                 */
                .map(splitDetailsItems -> (LinkedList<SplitDetailsItemVO>) mergeDstSplitDetailsItemsIfNeeded(splitDetailsItems, dstAirportCode))
                .map(splitDetailsItems ->
                        splitDetailsItems.stream()
                                .map(splitItem -> {
                                    if (splitItem.getPieces() == 0){
                                        splitItem.setPieces(null);
                                    }
                                    return splitItem;
                                })
                                .collect(Collectors.toCollection(LinkedList::new))
                )
                .collect(Collectors.toList());
    }

    /**
     * Split can have only ONE outgoing flight from origin. If there are more `than one,
     * we divide such split into few.
     *
     * Algorithm overview:
     * 0. It number of flights from Origin == 1, just return SplitDetails
     *
     * 1. Take the first flight from origin.
     * Build the Pieces availability map for it, where key=airport unique id, value=[AvailablePieces, NotAvailable]
     *
     * 2. Based on pieces Availability Map we've got
     * 2.1. cut SplitDetails from original SplitDetails, where availablePieces > 0
     * As a result, we've got SplitDetails with only one flight from origin,
     * where number of pieces correctly calculated for all airports
     *
     * 2.2. cut SplitDetails from original SplitDetails, where availablePieces < 0.
     * It's a SplitDetails left after cutting, with correspondingly decreased number of pieces.
     *
     * 3. Recursively repeat algorithm for the SplitDetails we've got in p.2.2.
     *
     * @return List of SplitDetails where each have only one flight from origin
     */
    private List<List<SplitDetailsItemVO>> divideSplitByOriginIfNeeded(
            List<SplitDetailsItemVO> splitDetails,
            String originAirportCode,
            Map<String, Integer> plannedPieces,
            DLVPiecesUsageVO dlvPiecesUsage) {
        var originAirports = splitDetails.stream()
                .filter(splitDetail -> splitDetail.getOriginAirportCode().equals(originAirportCode))
                .collect(Collectors.toList());

        if (originAirports.size() == 1) {
            return List.of(splitDetails);
        }

        var originAirport = originAirports.get(0);

        var plannedPiecesAvailabilityMap = new HashMap<String, Pair<Integer, Integer>>();
        buildPlannedPiecesAvailabilityMap(
                originAirport.getItemId(),
                plannedPieces.get(originAirport.getItemId()),
                splitDetails,
                plannedPiecesAvailabilityMap,
                plannedPieces
        );

        var actualPiecesAvailabilityMap = splitDetails.stream()
                .flatMap(item ->
                        Stream.concat(
                                Stream.of(item),
                                item.getSubSplits().stream()
                        )
                )
                .collect(Collectors.toMap(SplitDetailsItemVO::getItemId, SplitDetailsItemVO::getPieces));

        var splitDetailsBasedOnAvailablePieces = cutSplitDetailsByPiecesAvailability(
                splitDetails,
                dlvPiecesUsage,
                item -> plannedPiecesAvailabilityMap.containsKey(item.getItemId())
                        && plannedPiecesAvailabilityMap.get(item.getItemId()).getLeft() > 0,
                item ->
                        getPiecesFromAvailabilityMap(
                                item.getItemId(),
                                plannedPiecesAvailabilityMap.get(item.getItemId()).getLeft(),
                                actualPiecesAvailabilityMap
                        )
        );
        var splitDetailsBasedOnNotAvailablePieces = cutSplitDetailsByPiecesAvailability(
                splitDetails,
                dlvPiecesUsage,
                item -> !plannedPiecesAvailabilityMap.containsKey(item.getItemId())
                        || plannedPiecesAvailabilityMap.get(item.getItemId()).getRight() > 0,
                item ->
                        getPiecesFromAvailabilityMap(
                                item.getItemId(),
                                plannedPiecesAvailabilityMap.containsKey(item.getItemId())
                                        ? plannedPiecesAvailabilityMap.get(item.getItemId()).getRight()
                                        : item.getPieces(),
                                actualPiecesAvailabilityMap
                        )
        );

        return List.of(
                splitDetailsBasedOnAvailablePieces,
                divideSplitByOriginIfNeeded(splitDetailsBasedOnNotAvailablePieces, originAirportCode, plannedPieces, dlvPiecesUsage).stream()
                        .flatMap(List::stream)
                        .collect(Collectors.toCollection(LinkedList::new))
        );
    }

    private Integer getPiecesFromAvailabilityMap(
            String itemId,
            Integer availablePlannedPieces,
            Map<String, Integer> actualPiecesAvailabilityMap) {
        var availableActualPieces = actualPiecesAvailabilityMap.get(itemId);
        if (availableActualPieces <= availablePlannedPieces) {
            actualPiecesAvailabilityMap.put(itemId, 0);
            return availableActualPieces;
        } else {
            actualPiecesAvailabilityMap.put(itemId, availableActualPieces - availablePlannedPieces);
            return availablePlannedPieces;
        }
    }

    /**
     *
     * @return - The SplitDetails matching the filterCondition,
     * with pieces calculated based on getPiecesFromAvailabilityMap function
     */
    private List<SplitDetailsItemVO> cutSplitDetailsByPiecesAvailability(
            List<SplitDetailsItemVO> splitDetails,
            DLVPiecesUsageVO dlvPiecesUsage,
            Predicate<SplitDetailsItemVO> filterCondition,
            ToIntFunction<SplitDetailsItemVO> getPiecesFromAvailabilityMap) {
        var linksCorrection = new HashMap<String, String>();
        return splitDetails.stream()
                .filter(filterCondition)
                .map(item -> {
                    if (item.getSubSplits().isEmpty()) {
                        var shouldBuildDeliveredCopy = Optional.ofNullable(item.getMilestoneStatus())
                                .map(status ->
                                        status.equals(MilestoneNameEnum.DELIVERED.getLabel())
                                        || status.equals(MilestoneNameEnum.PARTIALLY_DELIVERED.getLabel())
                                )
                                .orElse(false);
                        if ( shouldBuildDeliveredCopy) {
                            return buildDeliveredSplitDetailCopy(
                                    item,
                                    getPiecesFromAvailabilityMap.applyAsInt(item),
                                    List.of(),
                                    dlvPiecesUsage
                            );
                        } else {
                            return buildSplitDetailCopy(
                                    item,
                                    getPiecesFromAvailabilityMap.applyAsInt(item),
                                    List.of()
                            );
                        }
                    } else {
                        var subItems = item.getSubSplits().stream()
                                .filter(filterCondition)
                                .map(subItem ->
                                        buildSplitDetailCopy(
                                                subItem,
                                                getPiecesFromAvailabilityMap.applyAsInt(subItem),
                                                List.of()
                                        )
                                )
                                .collect(Collectors.toList());

                        if (subItems.isEmpty()) {
                            return buildSplitDetailCopy(
                                    item,
                                    getPiecesFromAvailabilityMap.applyAsInt(item),
                                    List.of()
                            );
                        } else if (subItems.size() == 1) {
                            linksCorrection.put(item.getItemId(), subItems.get(0).getItemId());
                            return subItems.get(0);
                        } else {
                            return buildSplitDetailCopy(
                                    item,
                                    getPiecesFromAvailabilityMap.applyAsInt(item),
                                    subItems
                            );
                        }
                    }
                })
                .collect(Collectors.toList())
                .stream()
                .map(item -> {
                    if (linksCorrection.containsKey(item.getNextItemId())) {
                        item.setNextItemId(linksCorrection.get(item.getNextItemId()));
                    }
                    item.setSubSplits(
                            item.getSubSplits().stream()
                                    .map(subItem -> {
                                        if (linksCorrection.containsKey(subItem.getNextItemId())) {
                                            subItem.setNextItemId(linksCorrection.get(subItem.getNextItemId()));
                                        }
                                        return subItem;
                                    })
                                    .collect(Collectors.toList())
                    );
                    return item;
                })
                .collect(Collectors.toCollection(LinkedList::new));
    }

    private String determineMilestoneStatusForDeliveredSplitDetailCopy(
            Integer pieces,
            Pair<Integer, Integer> deliveredAndReturnedPieces,
            DLVPiecesUsageVO airportDlvPiecesUsage) {
        if (deliveredAndReturnedPieces.getRight() > 0) {
            airportDlvPiecesUsage.setUnusedDRNPieces( airportDlvPiecesUsage.getUnusedDRNPieces() - deliveredAndReturnedPieces.getRight() );
            airportDlvPiecesUsage.setUnusedDLVPieces(airportDlvPiecesUsage.getUnusedDLVPieces() - deliveredAndReturnedPieces.getRight());
        }
        if (deliveredAndReturnedPieces.getLeft() >= pieces) {
            airportDlvPiecesUsage.setUnusedDLVPieces(airportDlvPiecesUsage.getUnusedDLVPieces() - deliveredAndReturnedPieces.getLeft());
            return MilestoneNameEnum.DELIVERED.getLabel();
        } else if (deliveredAndReturnedPieces.getLeft() > 0) {
            airportDlvPiecesUsage.setUnusedDLVPieces(airportDlvPiecesUsage.getUnusedDLVPieces() - deliveredAndReturnedPieces.getLeft());
            return MilestoneNameEnum.PARTIALLY_DELIVERED.getLabel();
        } else {
            return MilestoneNameEnum.ARRIVED.getLabel();
        }
    }
    private SplitDetailsItemVO buildDeliveredSplitDetailCopy(
            SplitDetailsItemVO item,
            Integer pieces,
            List<SplitDetailsItemVO> subItems,
            DLVPiecesUsageVO dlvPiecesUsage) {
        var deliveredAndReturned = getDeliveredAndReturnedPieces(
                true,
                pieces,
                dlvPiecesUsage.getDeliveryUsageByAirport(item.getItemId())
        );
        var milestoneStatus = determineMilestoneStatusForDeliveredSplitDetailCopy(
                pieces,
                deliveredAndReturned,
                dlvPiecesUsage.getDeliveryUsageByAirport(item.getItemId())
        );

        return new SplitDetailsItemVO(
                item.getItemId(),
                item.getNextItemId(),
                item.getOriginAirportCode(),
                milestoneStatus,
                item.getMilestoneTime(),
                item.getMilestoneTimePostfix(),
                pieces,
                item.getCarrierCode(),
                item.getFlightNumber(),
                item.getActualFlightData(),
                subItems
        );
    }

    private SplitDetailsItemVO buildSplitDetailCopy(SplitDetailsItemVO item, Integer pieces, List<SplitDetailsItemVO> subItems) {
        return new SplitDetailsItemVO(
                item.getItemId(),
                item.getNextItemId(),
                item.getOriginAirportCode(),
                item.getMilestoneStatus(),
                item.getMilestoneTime(),
                item.getMilestoneTimePostfix(),
                pieces,
                item.getCarrierCode(),
                item.getFlightNumber(),
                item.getActualFlightData(),
                subItems
        );
    }

    /**
     * Build the pieces availability map.
     *
     * 1. Take the first flight from origin.
     * The number of pieces it is curing - is the number of pieces available for the all Airport going next.
     *
     * 2. Take the next connected Airport, and based on number of available pieces coming to it,
     * calculate available and not available pieces for it.
     *
     * 3. Repeat the p1.2. for all next connected Airports
     *
     * 4. Save the got result to the map, where key=airport unique id, value=[AvailablePieces, NotAvailable]
     */
    private void buildPlannedPiecesAvailabilityMap(
            String itemId,
            Integer piecesLimit,
            List<SplitDetailsItemVO> splitDetails,
            Map<String, Pair<Integer, Integer>> plannedPiecesAvailabilityMap,
            Map<String, Integer> plannedPieces) {
        var splitDetailsItem = findSplitDetailItemByItemId(itemId, splitDetails);
        var itemPiecesAvailability = calcPiecesAvailability(plannedPieces.get(splitDetailsItem.getItemId()), piecesLimit);
        plannedPiecesAvailabilityMap.put(
                splitDetailsItem.getItemId(),
                itemPiecesAvailability
        );

        var nextItemId = splitDetailsItem.getNextItemId();
        if (nextItemId != null) {
            buildPlannedPiecesAvailabilityMap(nextItemId, itemPiecesAvailability.getLeft(), splitDetails, plannedPiecesAvailabilityMap, plannedPieces);
        } else {
            var subItems = splitDetailsItem.getSubSplits().stream()
                    .sorted(Comparator.comparing(SplitDetailsItemVO::getPieces).reversed())
                    .collect(Collectors.toList());
            var remainingPieces = itemPiecesAvailability.getLeft();
            for(var subItem : subItems) {
                var subItemPiecesAvailability = calcPiecesAvailability(plannedPieces.get(subItem.getItemId()), remainingPieces);
                plannedPiecesAvailabilityMap.put(
                        subItem.getItemId(),
                        subItemPiecesAvailability
                );

                remainingPieces -= subItemPiecesAvailability.getLeft();
                buildPlannedPiecesAvailabilityMap(subItem.getNextItemId(), subItemPiecesAvailability.getLeft(), splitDetails, plannedPiecesAvailabilityMap, plannedPieces);
            }
        }
    }

    private Pair<Integer, Integer> calcPiecesAvailability(Integer currentPieces, Integer piecesLimit) {
        var availablePieces = piecesLimit >= currentPieces ? currentPieces : piecesLimit;
        var notAvailablePieces = piecesLimit >= currentPieces ? 0 : currentPieces - piecesLimit;
        return Pair.of(availablePieces, notAvailablePieces);
    }


    private SplitDetailsItemVO findSplitDetailItemByItemId(
            String itemId,
            List<SplitDetailsItemVO> splitDetails) {
        return splitDetails.stream()
                .filter(item -> item.getItemId().equals(itemId))
                .findFirst()
                .orElseThrow(() -> {
                    log.info("Can't find SplitDetailsItemVO by itemId: {}", itemId);
                    return new RuntimeException("Can't find SplitDetailsItemVO by itemId: " + itemId);
                });
    }

    /**
     * Build Airports from plans.
     */
    private List<SplitAirportVO> buildAirports(
            List<ShipmentMilestonePlanVO> awbPlans,
            String originAirportCode,
            String dstAirportCode
    ){
        //Group ARR and DEP plans by AirportCode
        var plansByAirport = awbPlans.stream()
                .filter(plan ->
                        plan.getMilestoneCode().equals(MilestoneCodeEnum.DEP)
                                || plan.getMilestoneCode().equals(MilestoneCodeEnum.ARR)
                )
                .collect(Collectors.groupingBy(ShipmentMilestonePlanVO::getAirportCode));

        /**
         * For each list of Plans related to particular AirportCode,
         * build the Airport dependent on whether Airport from Origin, Destination or Transfer station.
         *
         * Note1: See details for each case below
         * Note2: We don't calculate the statuses there, it'll be done during the Split building.
         * There we just determine the Airports considering the Plans only.
         */
        return plansByAirport.entrySet().stream()
                .flatMap(entry -> {
                    var airportCode = entry.getKey();
                    var airportPlans = entry.getValue();

                    /**
                     * 1) If Airport from origin, build airport where
                     * - itemId = first Plan serial number
                     * - ARR plans = [] (since Airport from origin has no ARR plans)
                     * - DEP plans = all plans (since Airport from Origin has only DEP plans there)
                     */
                    if (airportCode.equals(originAirportCode) ) {
                        return airportPlans.stream()
                                .map(srcPlan ->
                                        new SplitAirportVO(
                                                srcPlan.getSerialNumber().toString(),
                                                airportCode,
                                                Collections.emptyList(),
                                                List.of(srcPlan)
                                        )
                                );

                    /**
                     * 2) If Airport from destination, build airport where
                     * - itemId = first Plan serial number
                     * - ARR plans = all plans (since Airport from destination has only DEP plans there)
                     * - DEP plans = [] (since Airport from destination has no DEP plans)
                    */
                    } else if (airportCode.equals(dstAirportCode)) {
                        return airportPlans.stream()
                                .map(dstPlan ->
                                        new SplitAirportVO(
                                                dstPlan.getSerialNumber().toString(),
                                                airportCode,
                                                List.of(dstPlan),
                                                Collections.emptyList()
                                        )
                                );

                    /**
                     * 3) If transfer Airport.
                     *
                     * Each Airport could has
                     * - from 0 to infinity of ARR plans as income.
                     * - form 0 to infinity of DEP plans as outcome.
                     *
                     * Here we have ALL the plans related to particular Airport Code.
                     * But since we could have several splits, and appropriately several representation of particular airport
                     * we have to separate each such airport, and found plans related to it.
                     *
                     * Since The sum of all incoming pieces should be equal to sum of all outcoming pieces, the idea is
                     * 1. Generate all possible combinations of ARR and DEP plans
                     * 2. Find all combination matches based on pieces sum
                     * 3. Find matches with the lowest cost (lowest number of plans in match),
                     * considering that each plan could be used only ones and all plans are used
                     *
                     * As a result build an airport where
                     * - itemId = first Plan serial number
                     * - ARR plans = all matched ARR plans
                     * - DEP plans = all matched DEP plans
                     */
                    } else {
                        var airportArrPlans = airportPlans.stream()
                                .filter(plan -> plan.getMilestoneCode().equals(MilestoneCodeEnum.ARR))
                                .collect(Collectors.toList());
                        var airportDepPlans = airportPlans.stream()
                                .filter(plan -> plan.getMilestoneCode().equals(MilestoneCodeEnum.DEP))
                                .collect(Collectors.toList());

                        var arrCombinations = generateCombinations(airportArrPlans);
                        var depCombinations = generateCombinations(airportDepPlans);
                        var combinationMatches = matchCombinations(arrCombinations, depCombinations);

                        var airportMatches = filterMinCostMatches(combinationMatches);
                        return airportMatches.stream()
                                .map(match ->
                                        new SplitAirportVO(
                                                buildAirportItemId(match),
                                                airportCode,
                                                match.getArr().getPlans(),
                                                match.getDep().getPlans()
                                        )
                                );
                    }
                })
                .collect(Collectors.toList());
    }

    /**
     * Build Flights from Plans
     */
    private List<SplitFlightVO> buildFlights(
            List<ShipmentMilestonePlanVO> depPlans,
            List<ShipmentMilestonePlanVO> arrPlans) {
        /**
         * To avoid Flight duplicates we should to make sure each ARR Plan will be used only ones.
         * So we wrap each Plan with MilestonePlanUsageVO which keeps the Plan and 'isUsed' flag
         */
        var arrPlansWithFlags = arrPlans.stream()
                .map(plan -> new MilestonePlanUsageVO(plan, false))
                .collect(Collectors.toList());

        /**
         * For each DEP Plan find unused matched ARR plan
         * and combine them together as Flight.
         *
         * Match condition:
         * AirportCodes, Pieces, FlightNumber - are equal
         * MilestoneCode = ARR
         * Arrived MilestoneTime should be after Departure MilestoneTime
         */
        return depPlans.stream()
                .map(depPlan -> {
                    var arrPlan = searchForUnusedPlan(
                            arrPlansWithFlags,
                            plan -> !plan.getAirportCode().equals(depPlan.getAirportCode())
                                    && plan.getMilestoneCode().equals(MilestoneCodeEnum.ARR)
                                    && plan.getPieces().equals(depPlan.getPieces())
                                    && plan.getFlightNumber().equals(depPlan.getFlightNumber())
                                    && plan.getMilestoneTimeUTC().isAfter(depPlan.getMilestoneTimeUTC())
                    );
                    return new SplitFlightVO(
                            depPlan,
                            arrPlan.orElseThrow(() -> {
                                log.info("Can't find ARR plan for DEP plan {}", depPlan);
                                return new RuntimeException("Can't find ARR plan for DEP plan " + depPlan.getSerialNumber());
                            })
                    );
                })
                .collect(Collectors.toList());
    }

    /**
     * If Split has several Airports from destination, merge them into one.
     */
    private List<SplitDetailsItemVO> mergeDstSplitDetailsItemsIfNeeded(
            List<SplitDetailsItemVO> splitDetails,
            String dstAirportCode) {
        var dstSplitDetailsItems = splitDetails.stream()
                .filter(item -> item.getOriginAirportCode().equals(dstAirportCode))
                .collect(Collectors.toList());

        if (dstSplitDetailsItems.size() < 2) {
            return splitDetails;
        }

        var oldsDstItemsIds = dstSplitDetailsItems.stream()
                .map(SplitDetailsItemVO::getItemId)
                .collect(Collectors.toSet());

        //Merge all SplitDetailsItemVO(from destination) into one
        var newMergedDst = mergeListOfDstIntoOneItem(dstSplitDetailsItems);

        var correctedSplitDetails = splitDetails.stream()
                //Remove old SplitDetailsItemVO(from destination) from the Split
                .flatMap(item ->
                        oldsDstItemsIds.contains(item.getItemId())
                                ? Stream.empty()
                                : Stream.of(item)
                )
                //All SplitDetailsItemVO referring to the removed SplitDetailsItemVO, should refer to the new SplitDetailsItemVO
                .map(item -> {
                    if (item.getNextItemId() != null) {
                        if (oldsDstItemsIds.contains(item.getNextItemId())) {
                            item.setNextItemId(newMergedDst.getItemId());
                        }
                    } else {
                        item.setSubSplits(
                                item.getSubSplits().stream()
                                        .map(subItem -> {
                                            if (oldsDstItemsIds.contains(subItem.getNextItemId())) {
                                                subItem.setNextItemId(newMergedDst.getItemId());
                                            }
                                            return subItem;
                                        })
                                        .collect(Collectors.toList())
                        );
                    }
                    return item;
                })
                .collect(Collectors.toCollection(LinkedList::new));

        //Add new SplitDetailsItemVO  to the end of Split
        correctedSplitDetails.add(newMergedDst);

        return correctedSplitDetails;
    }

    /**
     * Merge list of SplitDetailsItemVO (from destination) into one.
     * - Some parameters are just left the same, like AirportCode, CarrierCode, FlightNumber etc.
     * - Some parameters should be calculated additionally, like  MilestoneStatus, MilestoneTime etc.
     * To find details of such calculations, refer to function doing it
     */
    private SplitDetailsItemVO mergeListOfDstIntoOneItem(List<SplitDetailsItemVO> dstItems) {
        var newItemId = dstItems.get(0).getItemId();

        return dstItems.stream()
                .reduce(
                        null,
                        (result, item) -> {
                            if (result == null) {
                                return item;
                            }

                            result.setItemId(newItemId);
                            result.setNextItemId(item.getNextItemId());
                            result.setOriginAirportCode(item.getOriginAirportCode());
                            result.setCarrierCode(item.getCarrierCode());
                            result.setFlightNumber(item.getFlightNumber());
                            result.setSubSplits(item.getSubSplits());

                            result.setMilestoneStatus(
                                    mergeDstMilestoneStatuses(result.getMilestoneStatus(),  item.getMilestoneStatus())
                            );
                            result.setMilestoneTime(
                                    mergeDstMilestoneTime(result.getMilestoneTime(), item.getMilestoneTime())
                                            .orElse(null)
                            );
                            result.setMilestoneTimePostfix(
                                    mergeDstMilestoneTimePostfix(result.getMilestoneTimePostfix(), item.getMilestoneTimePostfix())
                            );
                            result.setPieces(
                                    mergeDstPieces(result, item)
                            );
                            return result;
                        }
                );
    }

    /**
     * Merge Pieces of two SplitDetailsItemVO from destination.
     *
     * @return the sum of all SplitDetailsItemVO which already has some MilestoneStatus(Partially Arrived, ...)
     * Pieces of SplitDetailsItemVO with absent MilestoneStatus are not considered.
     */
    private Integer mergeDstPieces(SplitDetailsItemVO item1, SplitDetailsItemVO item2) {
        return Stream.of(item1, item2)
                .map(SplitDetailsItemVO::getPieces)
                .reduce(0, Integer::sum);
    }

    /**
     * Merge MilestoneTime of two SplitDetailsItemVO from destination.
     *
     * @return the max time.
     */
    private Optional<LocalDateTime> mergeDstMilestoneTime(LocalDateTime time1, LocalDateTime time2) {
        return Stream.concat(
                Optional.ofNullable(time1).stream(),
                Optional.ofNullable(time2).stream()
        )
                .max(LocalDateTime::compareTo);
    }

    /**
     * Merge MilestoneTimePostfix of two SplitDetailsItemVO from destination.
     *
     * @return:
     * - null if both are absent
     * - ACTUAL if both are ACTUAL
     * - SCHEDULED if at least one SCHEDULED
     */
    private String mergeDstMilestoneTimePostfix(String postfix1, String postfix2) {
        var postfixes = Stream.concat(
                Optional.ofNullable(postfix1).stream(),
                Optional.ofNullable(postfix2).stream()
        )
                .collect(Collectors.toList());
        if (postfixes.isEmpty()) {
            return null;
        } else {
            return postfixes.stream().allMatch(postfix -> postfix.equals(FlightTimePostfixEnum.ACTUAL.getLabel()))
                    ? FlightTimePostfixEnum.ACTUAL.getLabel()
                    : FlightTimePostfixEnum.SCHEDULED.getLabel();
        }
    }

    /**
     * Merge MilestoneStatuses of two SplitDetailsItemVO from destination.
     *
     * @return:
     * - null if both are absent
     * - PARTIALLY_ARRIVED if at least one PARTIALLY_ARRIVED
     * - ARRIVED if at least one ARRIVED
     * - DELIVERED if both are DELIVERED
     * - PARTIALLY_ARRIVED otherwise
     */
    private String mergeDstMilestoneStatuses(String mlStatus1, String mlStatus2) {
        var statuses = new LinkedList<String>();

        Optional.ofNullable(mlStatus1).ifPresent(statuses::add);
        Optional.ofNullable(mlStatus2).ifPresent(statuses::add);

        if (statuses.isEmpty()) {
            return null;
        } else if (statuses.size() == 1) { //pr1, null+any = partArr
            return MilestoneNameEnum.PARTIALLY_ARRIVED.getLabel();
        } else if (statuses.contains(MilestoneNameEnum.PARTIALLY_ARRIVED.getLabel())) { //pr2, partArr + any = partArr
            return MilestoneNameEnum.PARTIALLY_ARRIVED.getLabel();
        } else if (statuses.contains(MilestoneNameEnum.PARTIALLY_DELIVERED.getLabel())) { //pr3, arr|partDel|del + partDel = partDel
            return MilestoneNameEnum.PARTIALLY_DELIVERED.getLabel();
        } else if (statuses.stream().anyMatch(s -> s.equals(MilestoneNameEnum.ARRIVED.getLabel()))
                && statuses.stream().anyMatch(s -> s.equals(MilestoneNameEnum.DELIVERED.getLabel())) ) { //pr4, arr+del=partDel
            return MilestoneNameEnum.PARTIALLY_DELIVERED.getLabel();
        } else if (statuses.contains(MilestoneNameEnum.ARRIVED.getLabel())) { //pr5, both arr = arr
            return MilestoneNameEnum.ARRIVED.getLabel();
        } else { //pr6, both del = del
            return MilestoneNameEnum.DELIVERED.getLabel();
        }
    }


    /**
     * Search for unused Plan by predicate.
     * If some Plan is found, mark it as used.
     */
    private Optional<ShipmentMilestonePlanVO> searchForUnusedPlan(
            List<MilestonePlanUsageVO> plans,
            Predicate<ShipmentMilestonePlanVO> searchPredicate) {
        return plans
                .stream()
                .filter(planUsage ->
                        !planUsage.isUsed() && searchPredicate.test(planUsage.getPlan())
                )
                .findFirst()
                .map(planUsage -> {
                    planUsage.setUsed(true);
                    return planUsage.getPlan();
                });
    }

    /**
     * Build AirportItemId for transfer Airport from matched ARR and DEP plans combination.
     *
     * @return SerialNumber of first available ARR plan or DEP plan, or just return random number.
     * Just logically it shouldn't get to random number generation case at all,
     * but it added just in case if some Plans happened to be inconsistent.
     */
    private String buildAirportItemId(SplitPlanCombinationMatchVO match) {
        return match.getArr().getPlans().stream().findAny()
                .or(() -> match.getDep().getPlans().stream().findAny())
                .map(plan -> plan.getSerialNumber().toString())
                .orElseGet(() -> String.valueOf(random.nextLong()));
    }

    /**
     * Build SplitDetailsItemVO from Airport data considering Events.
     */
    private SplitDetailsItemVO buildSplitDetailsItemModel(
            String airportItemId,
            String airportCode,
            List<ShipmentMilestonePlanVO> arrPlans,
            List<ShipmentMilestonePlanVO> depPlans,
            List<MilestoneEventUsageVO> eventsWithUsage,
            List<MilestoneEventUsageVO> notMatchedEventsWithUsage,
            DLVPiecesUsageVO dlvPiecesUsage,
            Map<Long, String> depPlanSerialNumberToAirportItemId,
            Pair<String, String> originAndDstCodes,
            Map<String, Integer> plannedPieces) {
        var block = new SplitDetailsItemVO();
        block.setItemId(airportItemId);
        block.setOriginAirportCode(airportCode);
        block.setPieces(
                calculateSplitDetailsItemPieces(
                        airportItemId, airportCode, originAndDstCodes,
                        Pair.of(eventsWithUsage, notMatchedEventsWithUsage), arrPlans, depPlans
                )
        );
        plannedPieces.put(airportItemId, calculatePlannedSplitDetailsItemPieces(arrPlans, depPlans));

        var milestoneStatus = determineMilestoneStatus(
                airportItemId, airportCode, originAndDstCodes.getLeft(), originAndDstCodes.getRight(),
                Pair.of(eventsWithUsage, notMatchedEventsWithUsage), dlvPiecesUsage,
                arrPlans, depPlans
        );
        block.setMilestoneStatus(milestoneStatus);

        if (depPlans.isEmpty()) {
            //If no DEP plans
            block.setCarrierCode(null);
            block.setFlightNumber(null);
            block.setSubSplits(Collections.emptyList());
            block.setNextItemId(null);
        } else if (depPlans.size() == 1) {
            //If one DEP plans
            block.setCarrierCode(depPlans.get(0).getFlightCarrierCode());
            block.setFlightNumber(depPlans.get(0).getFlightNumber());
            block.setSubSplits(Collections.emptyList());
            block.setNextItemId(depPlanSerialNumberToAirportItemId.get(depPlans.get(0).getSerialNumber()));

            block.setActualFlightData(
                    buildActualFlightData(
                            airportItemId, depPlans.get(0), notMatchedEventsWithUsage, airportCode, milestoneStatus,
                            originAndDstCodes, arrPlans, depPlans
                    )
            );
        } else {
            //If more than 1 DST plan, for each of them we should build a separate SplitDetailsItemVO and push to subSplits
            block.setCarrierCode(null);
            block.setFlightNumber(null);
            block.setSubSplits(
                    depPlans.stream()
                            .map(depPlan ->
                                    buildSplitDetailsItemModel(
                                            airportItemId + "_" + depPlan.getSerialNumber(),
                                            airportCode,
                                            arrPlans,
                                            List.of(depPlan),
                                            eventsWithUsage,
                                            notMatchedEventsWithUsage,
                                            dlvPiecesUsage,
                                            depPlanSerialNumberToAirportItemId,
                                            originAndDstCodes,
                                            plannedPieces
                                    )
                            )
                            .collect(Collectors.toList())
            );
        }

        /**
         * Calculate MilestoneStatus, MilestoneTime, MilestoneTimePostfix
         * To find details of such calculations, refer to function doing it
         */

        var milestoneTimeAndTimePostfix = determineMilestoneTimeAndTimePostfix(
                airportItemId, airportCode, milestoneStatus, originAndDstCodes, eventsWithUsage, arrPlans, depPlans, true
        );
        block.setMilestoneTime(
                milestoneTimeAndTimePostfix
                        .map(Pair::getLeft)
                        .orElse(null)
        );
        block.setMilestoneTimePostfix(
                milestoneTimeAndTimePostfix
                        .map(Pair::getRight)
                        .orElse(null)
        );

        return block;
    }


    private ActualFlightDataVO buildActualFlightData(
            String airportItemId,
            ShipmentMilestonePlanVO plan,
            List<MilestoneEventUsageVO> notMatchedEventsWithUsage,
            String airportCode,
            String milestoneStatus,
            Pair<String, String> originAndDstCodes,
            List<ShipmentMilestonePlanVO> arrPlans,
            List<ShipmentMilestonePlanVO> depPlans) {
        return findEventByPlan(airportItemId, notMatchedEventsWithUsage, plan, false)
                .map(event -> {
                    var flightNumberAndCarrierCode = Optional.ofNullable(event.getTransactionDetails())
                            .map(transactionDetails -> (Map)transactionDetails)
                            .map(transactionDetailsMap ->
                                    Pair.of(
                                            (String)transactionDetailsMap.getOrDefault("flightNumber", null),
                                            (String)transactionDetailsMap.getOrDefault("flightCarrierCode", null)
                                    )
                            );
                    return new ActualFlightDataVO(
                            flightNumberAndCarrierCode.map(Pair::getRight).orElse(null),
                            flightNumberAndCarrierCode.map(Pair::getLeft).orElse(null),
                            determineMilestoneTimeAndTimePostfix(
                                    airportItemId, airportCode, milestoneStatus, originAndDstCodes, notMatchedEventsWithUsage, arrPlans, depPlans, false
                            )
                                    .map(Pair::getLeft)
                                    .orElse(null)
                    );
                })
                .orElse(null);
    }

    /**
     * Build MilestoneTime and TimePostfix for SplitDetailsItemVO.
     */
    private Optional<Pair<LocalDateTime, String>> determineMilestoneTimeAndTimePostfix(
            String airportItemId,
            String airportCode,
            String milestoneStatus,
            Pair<String, String> originAndDstCodes,
            List<MilestoneEventUsageVO> eventsWithUsage,
            List<ShipmentMilestonePlanVO> arrPlans,
            List<ShipmentMilestonePlanVO> depPlans,
            boolean considerFlightNumber) {
        var isFromDst = airportCode.equals(originAndDstCodes.getRight());
        var isFromOrigin = airportCode.equals(originAndDstCodes.getLeft());

        var shouldTransferStationGetDateFromArrPlans = milestoneStatus != null && (
                milestoneStatus.equals(MilestoneNameEnum.PARTIALLY_ARRIVED.getLabel())
                || milestoneStatus.equals(MilestoneNameEnum.ARRIVED.getLabel()));
        var shouldTransferStationGetDateFromDepPlans = milestoneStatus ==null
                || ( milestoneStatus.equals(MilestoneNameEnum.PARTIALLY_DEPARTED.getLabel())
                || milestoneStatus.equals(MilestoneNameEnum.DEPARTED.getLabel()) );

        if (isFromDst || (!isFromOrigin && shouldTransferStationGetDateFromArrPlans)) {
            /**
             * If Airport from destination
             *
             * Try to find Events for each- ARR plan and
             *
             * IF there are some Events matched
             * - find the Event with MAX UTC time, but take its local time
             * - TimePostfix appropriately will be ACTUAL
             *
             * IF there are NO events matched
             * - find the Plan with MAX UTC time, but take its local time
             * - TimePostfix appropriately will be SCHEDULED
             */
            return arrPlans.stream()
                    .flatMap(plan ->findEventByPlan(airportItemId, eventsWithUsage, plan, considerFlightNumber).stream())
                    .max(Comparator.comparing(ShipmentMilestoneEventVO::getMilestoneTimeUTC))
                    .map(lastEvent -> Pair.of(lastEvent.getMilestoneTime(), FlightTimePostfixEnum.ACTUAL.getLabel()))
                    .or(() ->
                    	arrPlans.stream()
                                    .max(Comparator.comparing(ShipmentMilestonePlanVO::getMilestoneTimeUTC))
                                    .map(lastPlan -> Pair.of(lastPlan.getMilestoneTime(), FlightTimePostfixEnum.SCHEDULED.getLabel()))
                    );
        } else if (isFromOrigin || (!isFromDst && shouldTransferStationGetDateFromDepPlans)) {
            /**
             * If Airport from origin or transfer stations
             *
             * Try to find Events for each- DEP  plan and
             *
             * IF there are some Events matched
             * - find the Event with MIN UTC time, but take its local time
             * - TimePostfix appropriately will be ACTUAL
             *
             * IF there are NO events matched
             * - find the Plan with MIN UTC time, but take its local time
             * - TimePostfix appropriately will be SCHEDULED
             */
            return depPlans.stream()
                    .flatMap(plan ->findEventByPlan(airportItemId, eventsWithUsage, plan, considerFlightNumber).stream())
                    .min(Comparator.comparing(ShipmentMilestoneEventVO::getMilestoneTimeUTC))
                    .map(firstEvent -> Pair.of(firstEvent.getMilestoneTime(), FlightTimePostfixEnum.ACTUAL.getLabel()))
                    .or(() ->
                            depPlans.stream()
                                    .min(Comparator.comparing(ShipmentMilestonePlanVO::getMilestoneTimeUTC))
                                    .map(firstPlan -> Pair.of(firstPlan.getMilestoneTime(), FlightTimePostfixEnum.SCHEDULED.getLabel()))
                    );
        } else {
            return Optional.empty();
        }
    }

    /**
     * Determine MilestoneStatus for SplitDetailsItemVO.
     *
     * Calculation depends on type of airport: Origin, Transfer or Destination
     * To find details of such calculations, refer to function doing it
     *
     * NOTE:
     * allArrived - means all ARR Plans have matched Events
     * anyArrived - means at least one ARR Plan has matched Events
     * allDeparted - means all DEP Plans have matched Events
     * anyDeparted - means at least one DEP Plan has matched Events
     */
    private String determineMilestoneStatus(
            String airportItemId,
            String airportCode,
            String originAirportCode,
            String dstAirportCode,
            Pair<List<MilestoneEventUsageVO>, List<MilestoneEventUsageVO>> allAndNotMatchedEventsWithUsage,
            DLVPiecesUsageVO dlvPiecesUsage,
            List<ShipmentMilestonePlanVO> arrPlans,
            List<ShipmentMilestonePlanVO> depPlans) {
        var allArrived = !arrPlans.isEmpty() && arrPlans.stream()
                .allMatch(plan -> findRegularOrNotMarchedEventByPlan(airportItemId, allAndNotMatchedEventsWithUsage, plan).isPresent());
        var anyArrived = allArrived || arrPlans.stream()
                .anyMatch(plan -> findRegularOrNotMarchedEventByPlan(airportItemId, allAndNotMatchedEventsWithUsage, plan).isPresent());

        var allDeparted = !depPlans.isEmpty() && depPlans.stream()
                .allMatch(plan -> findRegularOrNotMarchedEventByPlan(airportItemId, allAndNotMatchedEventsWithUsage, plan).isPresent());
        var anyDeparted = allDeparted || depPlans.stream()
                .anyMatch(plan -> findRegularOrNotMarchedEventByPlan(airportItemId, allAndNotMatchedEventsWithUsage, plan).isPresent());
        
        if (airportCode.equals(originAirportCode)) {
            return determineOriginAirportMilestoneStatus(allDeparted, anyDeparted);
        } else if (airportCode.equals(dstAirportCode)) {
            return determineDstAirportMilestoneStatus(airportItemId, arrPlans, dlvPiecesUsage, allArrived, anyArrived);
        } else {
            return determineTransferAirportMilestoneStatus(allDeparted, anyDeparted, allArrived, anyArrived);
        }
    }

    /**
     * Determine MilestoneStatus for SplitDetailsItemVO for Airport from origin.
     *
     * if allDeparted - DEPARTED
     * if anyDeparted - PARTIALLY_DEPARTED
     * if hasRSCHappened - ACCEPTED
     * otherwise - null
     */
    private String determineOriginAirportMilestoneStatus(
            boolean allDeparted,
            boolean anyDeparted) {

        if (allDeparted) {
            return MilestoneNameEnum.DEPARTED.getLabel();
        } else if (anyDeparted) {
            return MilestoneNameEnum.PARTIALLY_DEPARTED.getLabel();
        } else {
            return null;
        }
    }

    /**
     * Determine MilestoneStatus for SplitDetailsItemVO for Airport from destination.
     *
     * if hasDLVHappened - DELIVERED
     * if allArrived - ARRIVED
     * if anyArrived - PARTIALLY_ARRIVED
     * otherwise - null
     */
    private String determineDstAirportMilestoneStatus(
            String airportItemId,
            List<ShipmentMilestonePlanVO> arrPlans,
            DLVPiecesUsageVO dlvPiecesUsage,
            boolean allArrived,
            boolean anyArrived) {

        var plannedToArrivePieces = arrPlans.stream()
                .map(ShipmentMilestonePlanVO::getPieces)
                .reduce(0, Integer::sum);
        var deliveredAndReturnedPieces = getDeliveredAndReturnedPieces(allArrived, plannedToArrivePieces, dlvPiecesUsage);
        if (deliveredAndReturnedPieces.getRight() > 0) {
            dlvPiecesUsage.setUnusedDRNPieces( dlvPiecesUsage.getUnusedDRNPieces() - deliveredAndReturnedPieces.getRight() );
            dlvPiecesUsage.setUnusedDLVPieces( dlvPiecesUsage.getUnusedDLVPieces() - deliveredAndReturnedPieces.getRight() );
        }

        if (allArrived && deliveredAndReturnedPieces.getLeft() >= plannedToArrivePieces ) {
            dlvPiecesUsage.setUnusedDLVPieces(dlvPiecesUsage.getUnusedDLVPieces() - deliveredAndReturnedPieces.getLeft());
            dlvPiecesUsage.logDeliveryUsageByAirport(airportItemId, deliveredAndReturnedPieces);
            return MilestoneNameEnum.DELIVERED.getLabel();
        } else if (allArrived && deliveredAndReturnedPieces.getLeft() > 0) {
            dlvPiecesUsage.setUnusedDLVPieces(dlvPiecesUsage.getUnusedDLVPieces() - deliveredAndReturnedPieces.getLeft());
            dlvPiecesUsage.logDeliveryUsageByAirport(airportItemId, deliveredAndReturnedPieces);
            return MilestoneNameEnum.PARTIALLY_DELIVERED.getLabel();
        } else if (allArrived) {
            return MilestoneNameEnum.ARRIVED.getLabel();
        } else if (anyArrived) {
            return MilestoneNameEnum.PARTIALLY_ARRIVED.getLabel();
        } else {
            return null;
        }
    }

    private Pair<Integer, Integer> getDeliveredAndReturnedPieces(
            boolean allArrived,
            Integer arrivedPieces,
            DLVPiecesUsageVO dlvPiecesUsage) {
        var deliveredPieces = Math.min(dlvPiecesUsage.getUnusedDLVPieces(), arrivedPieces);

        if (allArrived && dlvPiecesUsage.getUnusedDRNPieces() > 0) {
            var returnedPieces = Math.min(dlvPiecesUsage.getUnusedDRNPieces(), arrivedPieces);
            return Pair.of(deliveredPieces - returnedPieces, returnedPieces);
        }

        return Pair.of(deliveredPieces, 0);
    }

    /**
     * Determine MilestoneStatus for SplitDetailsItemVO for Airport as transfer stations.
     *
     * if allDeparted - DEPARTED
     * if anyDeparted - PARTIALLY_DEPARTED
     * if allArrived - ARRIVED
     * if anyArrived - PARTIALLY_ARRIVED
     * otherwise - null
     */
    private String determineTransferAirportMilestoneStatus(
            boolean allDeparted,
            boolean anyDeparted,
            boolean allArrived,
            boolean anyArrived) {
        if (allDeparted) {
            return MilestoneNameEnum.DEPARTED.getLabel();
        } else if (anyDeparted) {
            return MilestoneNameEnum.PARTIALLY_DEPARTED.getLabel();
        } else if (allArrived) {
            return MilestoneNameEnum.ARRIVED.getLabel();
        } else if (anyArrived) {
            return MilestoneNameEnum.PARTIALLY_ARRIVED.getLabel();
        } else {
            return null;
        }
    }

    /**
     * Calculate Pieces for SplitDetailsItemVO
     *
     * if ARR plans are presented - sum the pieces of all Events related to it,
     * otherwise - sum the pieces of all DEP plans
     */
    private Integer calculateSplitDetailsItemPieces(
            String airportItemId,
            String airportCode,
            Pair<String, String> originAndDstCodes,
            Pair<List<MilestoneEventUsageVO>, List<MilestoneEventUsageVO>> allAndNotMatchedEventsWithUsage,
            List<ShipmentMilestonePlanVO> arrPlans,
            List<ShipmentMilestonePlanVO> depPlans) {
        if (airportCode.equals(originAndDstCodes.getRight())) {
            return arrPlans.stream()
                    .flatMap(arrPlan ->
                            findEventByPlan(airportItemId, allAndNotMatchedEventsWithUsage.getLeft(), arrPlan)
                                    .or(() -> findEventByPlan(airportItemId, allAndNotMatchedEventsWithUsage.getRight(), arrPlan, false))
                                    .stream()
                    )
                    .map(ShipmentMilestoneEventVO::getPieces)
                    .reduce(0, Integer::sum);
        } else {
            return depPlans.stream()
                    .map(ShipmentMilestonePlanVO::getPieces)
                    .reduce(0, Integer::sum);
        }
    }

    private Integer calculatePlannedSplitDetailsItemPieces(
            List<ShipmentMilestonePlanVO> arrPlans,
            List<ShipmentMilestonePlanVO> depPlans) {
        var plans = !depPlans.isEmpty()
                ? depPlans
                : arrPlans;
        return plans.stream()
                .map(ShipmentMilestonePlanVO::getPieces)
                .reduce(0, Integer::sum);
    }

    /**
     * We should make sure each Event is used only within specific Airport and its sub-splits
     * to avoid Events against Airports matching collision.
     *
     * airportItemId for 'parent' SplitDetailsItemVO looks like '111'
     * airportItemId for 'sub-splits' SplitDetailsItemVO looks like '111_222', where '111' - id of its 'parent'.
     *
     * Since 'sub-splits' could use events of 'parent' and vice versa - we remove here the '_222' part if it's presented,
     * and then compare the airportItemId of Airport currently we searching the event for, and the airportItemId
     * of Airport which is already used the event.
     */
    private boolean isEventAvailableForAirport(
            String airportItemId,
            MilestoneEventUsageVO eventWithUsage) {
        return Optional.ofNullable(eventWithUsage.getUsedByAirportItemId())
                .map(eventUsedById -> eventUsedById.replaceAll("_.*", "").equals(airportItemId.replaceAll("_.*", "")))
                .orElse(true);
    }

    private Optional<ShipmentMilestoneEventVO> findRegularOrNotMarchedEventByPlan(
            String airportItemId,
            Pair<List<MilestoneEventUsageVO>, List<MilestoneEventUsageVO>> allAndNotMatchedEventsWithUsage,
            ShipmentMilestonePlanVO plan) {
        return findEventByPlan(airportItemId, allAndNotMatchedEventsWithUsage.getLeft(), plan, true)
                .or(() -> findEventByPlan(airportItemId, allAndNotMatchedEventsWithUsage.getRight(), plan, false));
    }

    private Optional<ShipmentMilestoneEventVO> findEventByPlan(
            String airportItemId,
            List<MilestoneEventUsageVO> eventsWithUsage,
            ShipmentMilestonePlanVO plan) {
        return findEventByPlan(airportItemId, eventsWithUsage, plan, true);
    }

    /**
     * Find Event matching to the Plan of Airport
     *
     * Match condition:
     * - Event is available for aiport - see isEventAvailableForAirport for details
     * - AirportCodes, MilestoneCode, FlightNumber - are equal
     * - Event has less or equal amount of pieces
     *
     * If event is found - we mark it as used by the current Airport
     */
    private Optional<ShipmentMilestoneEventVO> findEventByPlan(
            String airportItemId,
            List<MilestoneEventUsageVO> eventsWithUsage,
            ShipmentMilestonePlanVO plan,
            boolean considerFlightNumber) {
        return eventsWithUsage.stream()
                .filter(eventWithUsage ->
                            isEventAvailableForAirport(airportItemId, eventWithUsage)
                                && eventWithUsage.getEvent().getAirportCode().equals(plan.getAirportCode())
                                && eventWithUsage.getEvent().getMilestoneCode().equals(plan.getMilestoneCode())
                                && eventWithUsage.getEvent().getPieces() <= plan.getPieces()
                                && Optional.ofNullable(considerFlightNumber ? eventWithUsage.getEvent().getTransactionDetails() : null)
                                    .flatMap(transactionDetails -> Optional.ofNullable( ((Map)transactionDetails).get("flightNumber") ))
                                    .map(flightNumber -> flightNumber.equals(plan.getFlightNumber()))
                                    .orElse(true)
                                //&& event.getMilestoneTimeUTC().isAfter(plan.getMilestoneTimeUTC())
                )
                .findFirst()
                .map(eventWithUsage -> {
                    eventWithUsage.setUsedByAirportItemId(airportItemId);
                    return eventWithUsage.getEvent();
                });
    }

    /**
     * Find the Split Transition stations
     *
     * stops - all Airports not from origin and destination
     * numberOfFlights - number of SplitDetailsItemVO refers to another SplitDetailsItemVO
     */
    private TransitStationVO buildTransitionStations(
            List<SplitDetailsItemVO> splitDetailsItems,
            String originAirportCode,
            String dstAirportCode) {
        var stops = splitDetailsItems.stream()
                .filter(splitItem ->
                        !splitItem.getOriginAirportCode().equals(originAirportCode)
                                && !splitItem.getOriginAirportCode().equals(dstAirportCode)
                )
                .map(SplitDetailsItemVO::getOriginAirportCode)
                .distinct()
                .collect(Collectors.toList());
        var numberOfFlights =  splitDetailsItems.stream()
                .map(splitItem ->
                        Optional.ofNullable(splitItem.getNextItemId())
                            .map(ignored -> 1)
                            .orElseGet(() ->
                                    (int) splitItem.getSubSplits()
                                            .stream()
                                            .filter(subSplitItem -> subSplitItem.getNextItemId() != null)
                                            .count()
                            )
                )
                .reduce(0, Integer::sum);
        return new TransitStationVO(numberOfFlights, stops);
    }

    /**
     * Find the Split Milestone Status
     *
     * if any of SplitDetailsItemVO is DELIVERED - then DELIVERED
     * otherwise - IN_PROGRESS
     */
    private String determineMilestoneStatus(List<SplitDetailsItemVO> splitDetailsItems) {
        return splitDetailsItems.stream()
                .anyMatch(item -> Optional.ofNullable(item.getMilestoneStatus()).map(status -> status.equals(SplitMilestoneStatus.DELIVERED.getLabel())).orElse(false) )
                    ? SplitMilestoneStatus.DELIVERED.getLabel()
                    : SplitMilestoneStatus.IN_PROGRESS.getLabel();
    }

    /**
     * Calculate the Split pieces
     *
     * @return the sum of pieces of all airports from origin
     */
    private Integer calculateSplitPieces(List<SplitDetailsItemVO> splitDetailsItems, String originAirportCode) {
        return splitDetailsItems.stream()
                .filter(item -> item.getOriginAirportCode().equals(originAirportCode))
                .map(SplitDetailsItemVO::getPieces)
                .reduce(0, Integer::sum);
    }

    /**
     * @return first unused (based on processedAirports list) Airport from origin
     */
    private Optional<SplitAirportVO> findOriginNotProcessedAirport(
            List<SplitAirportVO> airports,
            String originAirportCode,
            LinkedHashSet<SplitAirportVO> processedAirports) {
        return airports.stream()
                .filter(airport ->
                        airport.getAirportCode().equals(originAirportCode)
                        && !processedAirports.contains(airport)
                )
                .findFirst();
    }

    /**
     * Get the List of Airports connected with the current through the ARR plans and Flights
     */
    private List<SplitAirportVO> getAirportsConnectedByArr(
            SplitAirportVO airport,
            List<SplitFlightVO> flights,
            Map<Long, SplitAirportVO> airportsByDep) {
        return airport.getArr().stream()
                .flatMap(arrPlan ->
                        flights.stream().filter(flight -> flight.getArr().getSerialNumber().equals(arrPlan.getSerialNumber()))
                )
                .flatMap(flight -> Optional.ofNullable(airportsByDep.get(flight.getDep().getSerialNumber())).stream())
                .collect(Collectors.toList());
    }

    /**
     * Get the List of Airports connected with the current through the DEP plans and Flights
     */
    private List<SplitAirportVO> getAirportsConnectedByDep(
            SplitAirportVO airport,
            List<SplitFlightVO> flights,
            Map<Long, SplitAirportVO> airportsByArr) {
        return airport.getDep().stream()
                .flatMap(depPlan ->
                        flights.stream().filter(flight -> flight.getDep().getSerialNumber().equals(depPlan.getSerialNumber()))
                )
                .flatMap(flight -> Optional.ofNullable(airportsByArr.get(flight.getArr().getSerialNumber())).stream())
                .collect(Collectors.toList());
    }

    /**
     * Get map of Airport Item Ids connected to the current Airport through the DEP plans with serial number
     */
    private Map<Long, String> getAirportItemIdConnectedByDepSerialNumber(
            SplitAirportVO airport,
            List<SplitFlightVO> flights,
            Map<Long, SplitAirportVO> airportsByArr) {
        return airport.getDep().stream()
                .flatMap(depPlan ->
                        flights.stream()
                                .filter(flight -> flight.getDep().getSerialNumber().equals(depPlan.getSerialNumber()))
                )
                .flatMap(flight ->
                        Optional.ofNullable(airportsByArr.get(flight.getArr().getSerialNumber()))
                            .map(connectedAirport -> Pair.of(flight.getDep().getSerialNumber(), connectedAirport))
                            .stream()
                )
                .collect(Collectors.toMap(Pair::getLeft, depIdWithAirport -> depIdWithAirport.getRight().getItemId()));
    }

    /**
     * Generate all possible combinations of Plans
     */
    private List<SplitPlansCombinationVO> generateCombinations(List<ShipmentMilestonePlanVO> plans) {
        var res = new LinkedList<SplitPlansCombinationVO>();
        int plansNumber = plans.size();
        int combinationsNumber = (int) Math.pow(2d, plansNumber);
        for (int i = 1; i < combinationsNumber; i++) {
            String code = Integer.toBinaryString(combinationsNumber | i).substring(1);
            var combination = new LinkedList<ShipmentMilestonePlanVO>();
            for (int j = 0; j < plansNumber; j++) {
                if (code.charAt(j) == '1') {
                    combination.add(plans.get(j));
                }
            }
            res.add(new SplitPlansCombinationVO(
                    combination.stream()
                            .map(ShipmentMilestonePlanVO::getPieces)
                            .reduce(0, Integer::sum),
                    combination.size(),
                    combination
            ));
        }
        return res;
    }

    /**
     * @return all possible matches between ARR and DEP plans combinations sorted by the costs.
     */
    private List<SplitPlanCombinationMatchVO> matchCombinations(List<SplitPlansCombinationVO> arrCombs, List<SplitPlansCombinationVO> depCombs) {
        return arrCombs.stream()
                .flatMap(arrComb ->
                        depCombs.stream()
                                .filter(depComb ->  depComb.getSum().equals(arrComb.getSum()))
                                .map(depComb -> new SplitPlanCombinationMatchVO(arrComb.getCost() + depComb.getCost(), arrComb, depComb))
                )
                .sorted(Comparator.comparingInt(SplitPlanCombinationMatchVO::getCost))
                .collect(Collectors.toList());
    }

    /**
     * @return the ARR-DEP Plans matches with lowest costs covering all the Plans
     */
    private List<SplitPlanCombinationMatchVO> filterMinCostMatches(List<SplitPlanCombinationMatchVO> matchesToProcess) {
        var consistentMatches = new LinkedList<SplitPlanCombinationMatchVO>();


        while (!matchesToProcess.isEmpty()) {
            var matchWithMinConst = matchesToProcess.get(0);
            consistentMatches.add(matchWithMinConst);

            var i = matchesToProcess.iterator();
            while (i.hasNext()) {
                var usedArrPlans = matchWithMinConst.getArr().getPlans();
                var usedDepPlans = matchWithMinConst.getDep().getPlans();
                var matchToCheck = i.next();

                var arrIsUsed = matchToCheck.getArr().getPlans().stream().anyMatch(usedArrPlans::contains);
                var depIsUsed = matchToCheck.getDep().getPlans().stream().anyMatch(usedDepPlans::contains);

                if (arrIsUsed || depIsUsed) {
                    i.remove();
                }
            }
        }

        return consistentMatches;
    }

}
