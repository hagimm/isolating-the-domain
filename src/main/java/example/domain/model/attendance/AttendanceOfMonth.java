package example.domain.model.attendance;

import java.util.List;

/**
 * 月次勤怠
 */
public class AttendanceOfMonth {
    List<AttendanceOfDay> list;
    public AttendanceOfMonth(){}
    public AttendanceOfMonth(List<AttendanceOfDay> workTimes) {
        list = workTimes;
    }

    public List<AttendanceOfDay> list() {
        return list;
    }
}
