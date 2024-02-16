package com.ibsplc.neoicargo.stock.inttest.proxy;

import static com.ibsplc.neoicargo.stock.util.CalculationUtil.toLong;

import com.ibsplc.icargo.business.shared.document.vo.DocumentVO;
import com.ibsplc.icargo.business.shared.document.vo.SharedRangeVO;
import com.ibsplc.neoicargo.stock.proxy.DocumentTypeEProxy;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Primary
@Component
public class DocumentTypeMockEProxy implements DocumentTypeEProxy {

  @Override
  public Void validateRange(DocumentVO documentVO) {
    return null;
  }

  @Override
  public List<SharedRangeVO> mergeRanges(DocumentVO documentVO) {
    return new ArrayList<>(merge(documentVO));
  }

  @Override
  public List<SharedRangeVO> splitRanges(
      Collection<SharedRangeVO> originalRanges, Collection<SharedRangeVO> availableRanges) {
    return new ArrayList<>(split(originalRanges, availableRanges));
  }

  // logic was taken from shared module
  private Collection<SharedRangeVO> merge(DocumentVO documentVO) {
    Collection<SharedRangeVO> ranges = documentVO.getRange();

    SharedRangeVO oldrangeVO = null;
    SharedRangeVO newrangeVO = null;
    SharedRangeVO mergedRange = null;

    boolean isBroken = false;
    for (SharedRangeVO rangeVO : ranges) {
      oldrangeVO = rangeVO;
      for (SharedRangeVO rangeVO1 : ranges) {
        newrangeVO = rangeVO1;
        if (getPrevious(rangeVO.getFromrange()).equals(rangeVO1.getToRange())) {
          isBroken = true;
          mergedRange = mergeRange(rangeVO1, rangeVO);
          break;
        }
        if (rangeVO.getToRange().equals(getNext(rangeVO1.getToRange()))) {
          isBroken = true;
          mergedRange = mergeRange(rangeVO, rangeVO1);
          break;
        }
      }
      if (isBroken) {
        break;
      }
    }
    if (isBroken) {
      ranges.add(mergedRange);
      ranges.remove(oldrangeVO);
      ranges.remove(newrangeVO);
      // Call recursively
      merge(documentVO);
    }
    return ranges;
  }

  public String getNext(String range) {
    String finl = String.valueOf(Integer.parseInt(range) + 1);
    StringBuilder s = new StringBuilder(finl);
    int n = finl.length();
    if (n < 7) {
      s = new StringBuilder("0");
      n++;
      while (n < 7) {
        s.append("0");
        n++;
      }
      s.append(finl);
    }
    return s.toString();
  }

  public String getPrevious(String str) {
    char[] sArray = str.toCharArray();
    int carry = 1;
    for (int i = sArray.length - 1; i >= 0; i--) {
      int jj = sArray[i];
      jj -= carry;
      if (jj == 47) {
        jj = 57;
        carry = 1;
      } else if (jj == 64) {
        jj = 90;
        carry = 1;
      } else {
        carry = 0;
      }

      sArray[i] = (char) jj;
      if (carry == 0) {
        break;
      }
    }
    if (carry == 1) {
      return null;
    }
    return new String(sArray);
  }

  private SharedRangeVO mergeRange(SharedRangeVO first, SharedRangeVO second) {
    first.setToRange(second.getToRange());

    if (first.getRangeDate() != null && second.getRangeDate() != null) {
      if (first.getRangeDate().after(second.getRangeDate())) {
        first.setRangeDate(second.getRangeDate());
      }
    }

    return first;
  }

  public Collection<SharedRangeVO> split(
      Collection<SharedRangeVO> existingRanges, Collection<SharedRangeVO> rangesToRemove) {
    Collection<SharedRangeVO> resultingRanges = new ArrayList<>();
    resultingRanges.addAll(existingRanges);
    if (rangesToRemove != null) {
      for (SharedRangeVO rangeToRemoveVO : rangesToRemove) {
        resultingRanges = splitRanges(resultingRanges, rangeToRemoveVO);
      }
    }
    return resultingRanges;
  }

  private Collection<SharedRangeVO> splitRanges(
      Collection<SharedRangeVO> existingRanges, SharedRangeVO removableRangeVO) {
    Collection<SharedRangeVO> splittedRanges = new ArrayList<>();
    Collection<SharedRangeVO> tempRanges;
    if (existingRanges != null) {
      for (SharedRangeVO existingRangeVO : existingRanges) {
        tempRanges = splitGenerally(existingRangeVO, removableRangeVO);

        // this is to check whether splitting has occured or not
        if (tempRanges != null) {
          splittedRanges.addAll(tempRanges);
        }
      }
    }
    return splittedRanges;
  }

  private Collection<SharedRangeVO> splitGenerally(
      SharedRangeVO existingRangeVO, SharedRangeVO removableRangeVO) {
    Collection<SharedRangeVO> resultingVOs = null;

    long existingStartRange = toLong(existingRangeVO.getFromrange());
    long existingEndRange = toLong(existingRangeVO.getToRange());

    long removableStartRange = toLong(removableRangeVO.getFromrange());
    long removableEndRange = toLong(removableRangeVO.getToRange());

    /*
     * If the end range of removable vo is before the start range of existing vo
     * then, no need of splitting
     */
    if (removableEndRange < existingStartRange) {
      resultingVOs = new ArrayList<SharedRangeVO>();
      resultingVOs.add(existingRangeVO);
      return resultingVOs;
    }
    /*
     * If the start range of removable vo is after the end range of existing vo
     * then, no need of splitting
     */
    if (removableStartRange > existingEndRange) {
      resultingVOs = new ArrayList<>();
      resultingVOs.add(existingRangeVO);
      return resultingVOs;
    }

    /*
     * If the startRange of the removable range is In-Range with the
     * existing range, then a new range is to be created after the split
     */
    if ((existingStartRange - removableStartRange) < 0) {
      if (resultingVOs == null) {
        resultingVOs = new ArrayList<>();
      }
      SharedRangeVO vo = new SharedRangeVO();
      vo.setFromrange(existingRangeVO.getFromrange());
      vo.setToRange(getPrevious(removableRangeVO.getFromrange()));
      vo.setRangeDate(existingRangeVO.getRangeDate());
      resultingVOs.add(vo);
    }

    /*
     * If the endRange of the removable range is In-Range with the
     * existing range, then a new range is to be created after the split
     */
    if ((existingEndRange - removableEndRange) > 0) {
      if (resultingVOs == null) {
        resultingVOs = new ArrayList<>();
      }
      SharedRangeVO vo = new SharedRangeVO();
      vo.setFromrange(getNext(removableRangeVO.getToRange()));
      vo.setToRange(existingRangeVO.getToRange());
      vo.setRangeDate(existingRangeVO.getRangeDate());
      resultingVOs.add(vo);
    }

    return resultingVOs;
  }
}
