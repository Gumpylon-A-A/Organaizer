package ru.home.organaizer.entity;

import java.util.Objects;

/**
 * Created by Gumpylon Arsalan
 */

public class Client {

    private String id;
    private String fio;
    private String position;
    private String orgUnit;
    private String email;
    private String phoneNumber;

    public Client(String id, String fio, String position, String orgUnit, String email, String phoneNumber) {
        this.id = id;
        this.fio = fio;
        this.position = position;
        this.orgUnit = orgUnit;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }

    public String getId() {
        return id;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getOrgUnit() {
        return orgUnit;
    }

    public void setOrgUnit(String orgUnit) {
        this.orgUnit = orgUnit;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getFio() {
        return fio;
    }

    public void setFio(String fio) {
        this.fio = fio;
    }

    @Override
    public String toString() {
        return "| ID: " + id +
                " | ФИО: " + fio +
                " | Должность: " + position +
                " | Организация: " + orgUnit +
                " | Почта: " + email +
                " | Номер телефона: " + phoneNumber +
                " | ";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Client)) {
            return false;
        }
        Client client = (Client) o;
        return Objects.equals(getId(), client.getId()) &&
                Objects.equals(getFio(), client.getFio()) &&
                Objects.equals(getPosition(), client.getPosition()) &&
                Objects.equals(getOrgUnit(), client.getOrgUnit()) &&
                Objects.equals(getEmail(), client.getEmail()) &&
                Objects.equals(getPhoneNumber(), client.getPhoneNumber());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getFio(), getPosition(), getOrgUnit(), getEmail(), getPhoneNumber());
    }
}
