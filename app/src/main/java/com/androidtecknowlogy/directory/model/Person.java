package com.androidtecknowlogy.directory.model;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.List;

/**
 * Created by nezspencer on 12/6/16.
 */

public class Person {
    private String firstName;
    private String surname;
    private String department;
    private String level;
    private String faculty;
    private String hallOfResidence;
    private String birthday;
    private String birthMonth;
    private String roomNumber;
    private String blockName_RoomNumber;
    private List<String> churchSocieties;
    private String liturgicalGroup;
    private String phoneNumber;
    private String emailAddress;
    private String photoUrl;
    private String gender;
    private boolean residesInTown;

    /*mandatory empty constructor for firebase*/
    public Person() {
    }

    public Person(String firstName, String surname, String department, String level, String faculty,
                  String hallOfResidence, String birthday, String birthMonth, String roomNumber,
                  String blockName_RoomNumber, List<String> churchSocieties, String liturgicalGroup,
                  String phoneNumber, String emailAddress, String photoUrl, String gender, boolean residesInTown) {
        this.firstName = firstName;
        this.surname = surname;
        this.department = department;
        this.level = level;
        this.faculty = faculty;
        this.hallOfResidence = hallOfResidence;
        this.birthday = birthday;
        this.birthMonth = birthMonth;
        this.roomNumber = roomNumber;
        this.blockName_RoomNumber = blockName_RoomNumber;
        this.churchSocieties = churchSocieties;
        this.liturgicalGroup = liturgicalGroup;
        this.phoneNumber = phoneNumber;
        this.emailAddress = emailAddress;
        this.photoUrl = photoUrl;
        this.gender = gender;
        this.residesInTown = residesInTown;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getFaculty() {
        return faculty;
    }

    public void setFaculty(String faculty) {
        this.faculty = faculty;
    }

    public String getHallOfResidence() {
        return hallOfResidence;
    }

    public void setHallOfResidence(String hallOfResidence) {
        this.hallOfResidence = hallOfResidence;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getBirthMonth() {
        return birthMonth;
    }

    public void setBirthMonth(String birthMonth) {
        this.birthMonth = birthMonth;
    }

    public String getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(String roomNumber) {
        this.roomNumber = roomNumber;
    }

    public List<String> getChurchSocieties() {
        return churchSocieties;
    }

    public void setChurchSocieties(List<String> churchSocieties) {
        this.churchSocieties = churchSocieties;
    }

    public void addChurchSociety(String society){
        churchSocieties.add(society);
    }

    public String getLiturgicalGroup() {
        return liturgicalGroup;
    }

    public void setLiturgicalGroup(String liturgicalGroup) {
        this.liturgicalGroup = liturgicalGroup;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public String getBlockName_RoomNumber() {
        return blockName_RoomNumber;
    }

    public void setBlockName_RoomNumber(String blockName_RoomNumber) {
        this.blockName_RoomNumber = blockName_RoomNumber;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public boolean isResidesInTown() {
        return residesInTown;
    }

    public void setResidesInTown(boolean residesInTown) {
        this.residesInTown = residesInTown;
    }

    @Exclude
    public HashMap<String,Object> toMap()
    {
        HashMap<String,Object> map= new HashMap<>();
        map.put("firstName",firstName);
        map.put("surname",surname);
        map.put("department",department);
        map.put("level",level);
        map.put("faculty",faculty);
        map.put("hallOfResidence",hallOfResidence);
        map.put("birthday",birthday);
        map.put("birthMonth",birthMonth);
        map.put("roomNumber",roomNumber);
        map.put("churchSocieties",churchSocieties);
        map.put("liturgicalGroup",liturgicalGroup);
        map.put("phoneNumber",phoneNumber);
        map.put("emailAddress",emailAddress);
        map.put("photoUrl",photoUrl);
        map.put("gender",gender);

        return map;
    }
}
