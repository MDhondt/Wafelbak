package be.scoutsronse.wafelbak.service;

import be.scoutsronse.wafelbak.domain.id.WayId;
import com.sothawo.mapjfx.CoordinateLine;

import java.util.Collection;

public interface WayService {

    Collection<CoordinateLine> findCoordinateLinesBy(Collection<WayId> wayIds);
}