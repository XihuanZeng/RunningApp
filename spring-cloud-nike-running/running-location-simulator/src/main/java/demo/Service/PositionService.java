package demo.Service;

import demo.model.CurrentPosition;

/**
 * Created by xihuan on 18-6-11.
 */
public interface PositionService {

    // process position information, we cannot just send the currentPosition to distribution service, need some processing
    void processPositionInfo(long id, CurrentPosition currentPosition, boolean sendPositionToDistributionService);
}
