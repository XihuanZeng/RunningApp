package demo.Service;

import demo.model.CurrentPosition;

/**
 * Created by xihuan on 18-6-11.
 */
public interface PositionService {
    void processPositionInfo(long id, CurrentPosition currentPosition, boolean sendPositionToDistributionService);
}
