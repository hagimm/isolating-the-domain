package example.presentation.controller.attendance;

import example.domain.model.attendance.*;
import example.domain.model.worker.WorkerNumber;
import example.domain.type.time.ClockTime;

import javax.validation.constraints.AssertTrue;
import java.time.DateTimeException;

public class AttendanceForm {

    WorkerNumber workerNumber;
    WorkDay workDay;

    String startHour;
    String startMinute;
    String endHour;
    String endMinute;

    NormalBreakTime normalBreakTime;
    MidnightBreakTime midnightBreakTime;

    public AttendanceForm() {
        this.workDay = new WorkDay();
        this.startHour = "9";
        this.startMinute = "0";
        this.endHour = "17";
        this.endMinute = "30";
        this.normalBreakTime = new NormalBreakTime("60");
        this.midnightBreakTime = new MidnightBreakTime("0");
    }

    boolean startTimeValid = false;

    @AssertTrue(message = "開始時刻が不正です")
    public boolean isStartTimeValid() {
        if (startHour.isEmpty() || startMinute.isEmpty()) return false;

        try {
            workStartTime();
        } catch (NumberFormatException | DateTimeException ex) {
            return false;
        }

        return true;
    }

    boolean endTimeValid = false;

    @AssertTrue(message = "終了時刻が不正です")
    public boolean isEndTimeValid() {
        // TODO : 24時を超える終業時刻を入力できるようにする
        // TODO : 上記に対応したあとで開始時刻 < 終了時刻のチェックもする
        if (endHour.isEmpty() || endMinute.isEmpty()) return false;

        try {
            workEndTime();
        } catch (NumberFormatException | DateTimeException ex) {
            return false;
        }

        return true;
    }

    boolean workTimeValid = false;

    @AssertTrue(message = "終了時刻には開始時刻よりあとの時刻を入力してください")
    public boolean isWorkTimeValid() {
        if (!isStartTimeValid() || !isEndTimeValid()) return true;

        WorkStartTime workStartTime = workStartTime();
        WorkEndTime workEndTime = workEndTime();
        if (workStartTime.isAfter(workEndTime)) return false;

        return true;
    }

    private WorkStartTime workStartTime() {
        ClockTime clockTime = new ClockTime(Integer.valueOf(startHour), Integer.valueOf(this.startMinute));
        return new WorkStartTime(clockTime);
    }

    private WorkEndTime workEndTime() {
        ClockTime clockTime = new ClockTime(Integer.valueOf(endHour), Integer.valueOf(endMinute));
        return new WorkEndTime(clockTime);
    }

}
