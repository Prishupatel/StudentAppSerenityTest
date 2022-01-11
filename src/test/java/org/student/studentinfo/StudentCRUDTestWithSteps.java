package org.student.studentinfo;


import io.restassured.response.ValidatableResponse;
import net.serenitybdd.junit.runners.SerenityRunner;
import net.thucydides.core.annotations.Steps;
import net.thucydides.core.annotations.Title;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.student.testbase.TestBase;
import org.student.utils.TestUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.hamcrest.collection.IsMapContaining.hasValue;

@RunWith(SerenityRunner.class)
public class StudentCRUDTestWithSteps extends TestBase {
    static String firstName = "PrimeQA" + TestUtils.getRandomValue();
    static String lastName = "Automation" + TestUtils.getRandomValue();
    static String programme = "API Testing";
    static String email = TestUtils.getRandomValue() + "abc@gmail.com";
    static int studentId;


    @Steps
    StudentSteps studentSteps;

    @Title("This will create new student")
    @Test
    public void test001() {
        List<String> courseList = new ArrayList<>();
        courseList.add("Java8");
        courseList.add("Python");
        ValidatableResponse response = studentSteps.createStudent(firstName, lastName, email, programme, courseList);
    }

    @Title("Verify if the student was added to the application")
    @Test
    public void test002() {
        HashMap<String, Object> value = studentSteps.getStudentInfoByFirstname(firstName);
        Assert.assertThat(value, hasValue(firstName));
        studentId = (int) value.get("id");   //cast to int

    }

    @Title("Update the user information and verify the updated information")
    @Test
    public void test003(){
        firstName=firstName + "_version1";
        List<String>courseList =new ArrayList<>();
        courseList.add("C#");
        courseList.add("postman");
        studentSteps.updateStudent(studentId,firstName,lastName,email,programme,courseList).log().all().statusCode(200);
        HashMap<String,Object>value=studentSteps.getStudentInfoByFirstname(firstName);
        Assert.assertThat(value,hasValue(firstName));
    }

    @Title("Delete the student and verify if the student is deleted ")
    @Test
    public void test004(){
        studentSteps.deleteStudent(studentId).statusCode(204);
        studentSteps.getStudentById(studentId).statusCode(404);
    }
}
