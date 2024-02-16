package com.ibsplc.neoicargo.stock.inttest;

import com.ibsplc.neoicargo.framework.core.security.Claims;
import com.ibsplc.neoicargo.framework.core.security.LoginProfile;
import java.util.HashMap;
import java.util.function.Supplier;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

@TestConfiguration
class StockLoginProfileContractTest {

  @Bean
  @Primary
  Supplier<LoginProfile> loginProfileSupplier() {
    return () -> {
      var profile = new LoginProfile();
      profile.setAirportCode("TRV");
      profile.setCompanyCode("AV");
      profile.setCountry("IN");
      profile.setDefaultWarehouseCode("TRVWH01");
      profile.setOwnAirlineCode("AV");
      profile.setOwnAirlineIdentifier(1134);
      profile.setStationCode("TRV");
      profile.setUserId("ICOADMIN");
      profile.setFirstName("111111");
      profile.setIcgToken(
          "aWNTZXNzaW9uSWQ6SFhQZnVnJTJCUU1INHJUYldKWGNtYVEzWVJtY1FCV3h6cktBU09qTFJvQWpGT1Z0bk9iMXEwbHQ5c1dXaUU1WkZrNnN3VTAxaFNOdmQ5JTBEJTBBOFhKNHV6OWU5MUQlMkI1ZnF6TWs2Yw==");
      profile.setType(LoginProfile.TokenType.ICARGO_IDENTITY);
      profile.setLanguage("en-US");
      var additionalClaims = new HashMap<String, Object>();
      additionalClaims.put(Claims.USER_TIMEZONE, "Europe/Paris");
      profile.setAdditionalClaims(additionalClaims);
      return profile;
    };
  }

  @Test
  void test() {
    String actual = "test";
    String expected = "test";
    Assertions.assertEquals(actual, expected);
  }
}
