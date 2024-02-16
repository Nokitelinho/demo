package com.ibsplc.neoicargo.stock.util;

import static org.apache.commons.lang3.StringUtils.isNotBlank;
import static org.apache.commons.lang3.StringUtils.isNotEmpty;

import com.ibsplc.neoicargo.stock.vo.RangeVO;
import com.ibsplc.neoicargo.stock.vo.StockAllocationVO;
import com.ibsplc.neoicargo.stock.vo.StockRequestVO;
import com.ibsplc.neoicargo.stock.vo.StockVO;
import java.util.List;
import java.util.Optional;
import lombok.experimental.UtilityClass;
import org.apache.commons.lang3.StringUtils;
import org.springframework.lang.Nullable;

@UtilityClass
public class CalculationUtil {
  public static long toLong(String range) {
    return findLong(range);
  }

  private static long findLong(String range) {
    char[] sArray = range.trim().toCharArray();
    long base = 1;
    long sNumber = 0;
    for (int i = sArray.length - 1; i >= 0; i--) {
      sNumber += base * calculateBase(sArray[i]);
      int value = sArray[i];
      if (value > 57) {
        base *= 26;
      } else {
        base *= 10;
      }
    }
    return sNumber;
  }

  private static long calculateBase(char letter) {
    long longValue = letter;
    long base = 0;
    try {
      base = Integer.parseInt("" + letter);
    } catch (NumberFormatException numberFormatException) {
      base = longValue - 65;
    }
    return base;
  }

  public static long countNumberOfDocuments(long asciiStartRange, long asciiEndRange) {
    return (asciiEndRange - asciiStartRange) + 1;
  }

  public static int getNumberOfDocuments(List<RangeVO> ranges) {
    return ranges.stream()
        .map(rangeVO -> difference(rangeVO.getStartRange(), rangeVO.getEndRange()))
        .reduce(0, Integer::sum);
  }

  public static int findNumberOfDocuments(String rangeTo, String rangeFrom) {
    return difference(rangeFrom, rangeTo);
  }

  private static int difference(String rangeFrom, String rangeTo) {
    long difference = toLong(rangeTo) - toLong(rangeFrom);
    difference++;
    return (int) difference;
  }

  public static long calculateQuantity(StockAllocationVO stockAllocationVO) {
    return stockAllocationVO.getRanges().stream()
        .map(rangeVO -> difference(rangeVO.getStartRange(), rangeVO.getEndRange()))
        .reduce(0, Integer::sum);
  }

  public static String stockHolderLovFilterReplace(final String value) {
    return Optional.ofNullable(value)
        .filter(StringUtils::isNoneEmpty)
        .map(e -> e.replace('*', '%'))
        .orElse(null);
  }

  public static String subtract(String rangeString, long offset) {
    char[] sArray = rangeString.toCharArray();
    for (long k = 0; k < offset; k++) {
      var carry = 1;
      for (int i = sArray.length - 1; i >= 0; i--) {
        int charValue = sArray[i];
        charValue -= carry;
        if (charValue == 47) {
          charValue = 57;
          carry = 1;
        } else if (charValue == 64) {
          charValue = 90;
          carry = 1;
        } else {
          carry = 0;
        }

        sArray[i] = (char) charValue;
        if (carry == 0) {
          break;
        }
      }
    }

    return new String(sArray);
  }

  public static Long toLongNullable(@Nullable String value) {
    return isNotEmpty(value) && isNotBlank(value.trim()) ? toLong(value.trim()) : null;
  }

  public static Integer toIntegerNullable(@Nullable String value) {
    return isNotEmpty(value) && isNotBlank(value.trim()) ? Integer.parseInt(value.trim()) : null;
  }

  public static long getNumberOfDocs(StockRequestVO stockRequestVO, StockVO stockVO) {
    if (stockVO.getAutoprocessQuantity() < stockRequestVO.getRequestedStock()) {
      return stockVO.getAutoprocessQuantity();
    } else {
      return stockRequestVO.getRequestedStock();
    }
  }
}
