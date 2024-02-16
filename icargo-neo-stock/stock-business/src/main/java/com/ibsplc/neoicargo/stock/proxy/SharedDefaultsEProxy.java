package com.ibsplc.neoicargo.stock.proxy;

import com.ibsplc.neoicargo.framework.ebl.api.EProductProxy;
import java.util.Collection;
import java.util.Map;

@EProductProxy(module = "shared", submodule = "defaults", name = "sharedDefaultsEProxy")
public interface SharedDefaultsEProxy {
  Map<String, String> findSystemParameterByCodes(Collection<String> systemparameterCodes);
}
