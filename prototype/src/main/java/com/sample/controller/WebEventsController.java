package com.sample.controller;

import com.sample.view.WebView;
import org.fluentness.controller.AbstractWebController;
import org.fluentness.view.html.Html;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import java.util.List;

import static org.fluentness.view.html.HtmlAttribute.CLASS;
import static org.fluentness.view.html.HtmlAttribute.HREF;
import static org.fluentness.view.html.HtmlFactory.*;

public class WebEventsController extends AbstractWebController<WebView> {

    @BasePath("/events")
    public WebEventsController(WebView webView) {
        super(webView);
    }

    @Action
    Html events(int year, int month) {
        YearMonth current = (year == 0 && month == 0) ? YearMonth.now() : YearMonth.of(year, month);
        YearMonth previous = current.minusMonths(1);
        YearMonth next = current.plusMonths(1);
        List<LocalDate> days = getDays(current);
        return div(CLASS + "calendar",
            h2(
                i(CLASS + "icono-calendar"),
                current.format(DateTimeFormatter.ofPattern("MMMM y", request.get().getLocale())),
                div(CLASS + "right",
                    a(HREF + "/events?year=" + previous.getYear() + "&month=" + previous.getMonthValue(),
                        i(CLASS + "icono-caretLeftCircle")
                    ),
                    a(HREF + "/events?year=" + next.getYear() + "&month=" + next.getMonthValue(),
                        i(CLASS + "icono-caretRightCircle")
                    )
                )
            ),
            div(CLASS + "flex seven",
                forEach(days, day ->
                    span(CLASS + (day.getMonthValue() == current.getMonthValue() ? "current" : ""),
                        day.getDayOfMonth() + ""
                    )
                )
            )
        );
    }

    private List<LocalDate> getDays(YearMonth month) {

        List<LocalDate> days = new LinkedList<>();
        LocalDate firstDay = month.atDay(1);
        LocalDate lastDay = month.atDay(month.lengthOfMonth());

        for (int i = firstDay.getDayOfWeek().getValue() - 1; i > 0; i--) {
            days.add(firstDay.minusDays(i));
        }
        for (int j = 1; j < month.lengthOfMonth() + 1; j++) {
            days.add(month.atDay(j));
        }
        for (int k = 1; k < 8 - lastDay.getDayOfWeek().getValue(); k++) {
            days.add(lastDay.plusDays(k));
        }

        return days;
    }

}