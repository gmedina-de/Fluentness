package com.sample.controller;

import com.sample.model.Person;
import com.sample.view.PeopleView;
import org.fluentness.controller.Controller;
import org.fluentness.controller.QueryParameter;
import org.fluentness.controller.Route;
import org.fluentness.repository.Repository;
import org.fluentness.repository.RepositoryImpl;
import org.fluentness.routing.HttpMethod;
import org.fluentness.routing.HttpResponse;
import org.fluentness.view.View;

import java.util.List;

public class PeopleController implements Controller {

    private Repository<Person> personRepository = new RepositoryImpl<>(Person.class);

    @Route(path = "/")
    public HttpResponse list(
            @QueryParameter("name") String name,
            @QueryParameter("surname") String surname) {


        Person person = new Person()
                .setName(name)
                .setName("asdf")
                .setSurname(surname)
                .setSurname("asdf");


        person.create();

        List<Person> people = personRepository.list();


//        person = personRepository.find(19);
//        person.setSurname("testtttaa");
//        person.update();

        View view = new PeopleView()
                .set("people", people)
                .set("testBoolean", true);
        return render(view);
    }

    @Route(path = "/redirect", method = HttpMethod.POST)
    public HttpResponse testRedirect() {


        return redirect("http://www.google.com/");
    }

    @Route(path = "/response")
    public HttpResponse testResponse() {
        return response("this is a raw response with custom response code")
                .setStatusCode(201)
                .setHeader("asdf", "asdf");
    }
}