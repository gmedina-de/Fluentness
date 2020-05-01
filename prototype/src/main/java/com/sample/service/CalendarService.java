package com.sample.service;

import org.fluentness.controller.web.template.html.Html;
import org.fluentness.service.Service;

public interface CalendarService extends Service {
    Html renderMonthCalendar(int year, int month);
}