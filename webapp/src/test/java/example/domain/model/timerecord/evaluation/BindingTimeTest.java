package example.domain.model.timerecord.evaluation;

import example.domain.model.timerecord.timefact.*;
import example.domain.type.datetime.DateTime;
import example.domain.type.time.ClockTime;
import example.domain.type.time.ClockTimeRange;
import example.domain.type.time.Minute;
import example.presentation.controller.timerecord.InputEndTime;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BindingTimeTest {

    @ParameterizedTest
    @CsvSource({
            "9:00, 17:00, 480",
            "17:00, 9:00, 960",
            "9:00, 9:30, 30",
            "9:30, 10:00, 30",
            "20:00, 02:00, 360"})
    void 時刻間の時間が分で取得できる(String fromTime, String toTime, String rangeTime) {
        DateTime startDateTime = new DateTime("2000-01-01", fromTime);
        new BindingTime(new WorkRange(
                new StartDateTime(startDateTime),
                InputEndTime.from(toTime).endDateTime(startDateTime.date())
        ));
        ClockTime from = new ClockTime(fromTime);
        ClockTime to = new ClockTime(toTime);

        ClockTimeRange range = new ClockTimeRange(from, to);
        Minute result = range.minute();

        assertEquals(rangeTime, result.toString());
    }
}