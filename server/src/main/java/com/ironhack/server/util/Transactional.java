package com.ironhack.server.util;

import java.math.BigDecimal;

public interface Transactional {
    BigDecimal increaseAmount(BigDecimal addAmount);
    BigDecimal decreaseAmount(BigDecimal addAmount);
}
