package com.ibsplc.neoicargo.cca.component.feature.getccaassignees;

import com.ibsplc.neoicargo.admin.user.AdminUserWebAPI;
import com.ibsplc.neoicargo.admin.user.model.AdminUserDetailsModel;
import com.ibsplc.neoicargo.cca.modal.CcaAssigneesEdge;
import com.ibsplc.neoicargo.cca.modal.CcaAssigneesNode;
import com.ibsplc.neoicargo.cca.modal.CcaAssigneesPage;
import com.ibsplc.neoicargo.cca.modal.CcaSelectFilter;
import com.ibsplc.neoicargo.framework.tenant.jaxrs.client.RegisterJAXRSClient;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
@Slf4j
@RegisterJAXRSClient(clazz = AdminUserWebAPI.class, targetService = "neo-admin-business")
public class GetCcaAssigneesFeature {

    private final AdminUserWebAPI adminUserWebAPI;

    private static final Integer DEFAULT_PAGE_SIZE = 10;

    public CcaAssigneesPage perform(CcaSelectFilter ccaSelectFilter) {
        log.info("Invoked GetCcaAssigneesFeature feature");
        return new CcaAssigneesPage(
                adminUserWebAPI.fetchUserBasicDetails("ACT", "WEB").stream()
                        .filter(distinctByKey(AdminUserDetailsModel::getUsrIdr))
                        .map(userModel ->
                                new CcaAssigneesEdge(
                                        new CcaAssigneesNode(
                                                userModel.getUserFirstName()
                                                        + Optional.ofNullable(userModel.getUserLastName())
                                                            .filter(ln -> ln.length() > 0)
                                                            .map(ln -> " " + ln)
                                                            .orElse(""),
                                                userModel.getUsrIdr()
                                        )
                                )
                        )
                        .filter(edge -> {
                            var username = edge.getNode().getUsername();
                            if (ccaSelectFilter.isLoadFromFilter()
                                    && ccaSelectFilter.getFilter() != null
                                    && ccaSelectFilter.getFilter().size() > 0) {
                                return ccaSelectFilter.getFilter().stream()
                                        .anyMatch(f -> username.equalsIgnoreCase(f));
                            } else if (!ccaSelectFilter.isLoadFromFilter()
                                    && ccaSelectFilter.getSearch() != null
                                    && !ccaSelectFilter.getSearch().equals("")) {
                                return username.toLowerCase().contains(ccaSelectFilter.getSearch().toLowerCase());
                            } else {
                                return true;
                            }
                        })
                        .limit(Optional.ofNullable(ccaSelectFilter.getFirst()).orElse(DEFAULT_PAGE_SIZE))
                        .collect(Collectors.toList())
        );

    }

    private <T> Predicate<T> distinctByKey(Function<? super T, ?> keyExtractor) {
        var seen = new HashMap<>();
        return t -> seen.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
    }
}
