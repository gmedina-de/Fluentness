package com.sample.controllers;

import com.sample.models.Person;
import com.sample.views.PeopleView;
import org.fluentness.ann.Route;
import org.fluentness.dao.Repository;
import org.fluentness.dao.RepositoryImpl;
import org.fluentness.mvc.Controller;
import org.fluentness.net.HttpResponse;

import java.util.List;

public class PeopleController implements Controller {

    private Repository<Person> personRepository = new RepositoryImpl<>(Person.class);

    @Route(path = "/")
    public HttpResponse list() {

//        Person person = new Person()
//                .setId(12)
//                .setName("Thomas")
//                .setSurname("Mueller");

        List<Person> people = personRepository.list();

        Person person = personRepository.find(19);
        person.setSurname("testttt");
        personRepository.update(person);

        return render(new PeopleView(people));
    }

    @Route(path = "/redirect")
    public HttpResponse testRedirect() {
        return redirect("http://www.google.com/");
    }

    @Route(path = "/response")
    public HttpResponse testResponse() {
        return response("this is a raw response with custom response code")
                .setStatusCode(201)
                .setHeader("asdf","asdf");
    }
}
