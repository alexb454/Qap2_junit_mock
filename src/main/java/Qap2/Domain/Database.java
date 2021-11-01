package Qap2.Domain;

import Qap2.Domain.Appointment.AppointmentSlot;
import Qap2.Domain.Appointment.BloodDonationAppointment;
import Qap2.Domain.Donor.BloodDonor;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

public class Database {
    public List<AppointmentSlot> getAppointmentSlots(){
        ArrayList<AppointmentSlot> appointmentSlots = new ArrayList<AppointmentSlot>();
        AppointmentSlot appointmentSlot = new AppointmentSlot();
        appointmentSlot.setId(1);
        appointmentSlot.setLocation("85 Lester Pl. st. John's NL");
        appointmentSlot.setDate(LocalDate.of(2021, Month.MARCH, 02));
        appointmentSlot.setStarttime(LocalTime.NOON);
        appointmentSlot.setEndtime(LocalTime.MIDNIGHT);
        appointmentSlot.setBloodtype("A");
        appointmentSlots.add(appointmentSlot);
        return appointmentSlots;
    }
    public BloodDonor getDonor(){
        BloodDonor bloodDonor = new BloodDonor();
        bloodDonor.setId(1);
        bloodDonor.setFirstname("Jean-Luc");
        bloodDonor.setLastname("Cougar");
        bloodDonor.setDateofbirth(LocalDate.of(2000, Month.APRIL, 25));
        bloodDonor.setBloodtype("A");
        bloodDonor.setNextappointment(LocalDate.of(2022, Month.JANUARY, 13));
        bloodDonor.setLastdonationdate(LocalDate.of(2021, Month.MARCH, 03));
        return bloodDonor;
    }
    public BloodDonationAppointment getBloodDonationAppointment(int id){
        BloodDonationAppointment bloodDonationAppointment = new BloodDonationAppointment();
        bloodDonationAppointment.setAppointmentduration(LocalTime.of(12, 0));
        bloodDonationAppointment.setFirsttimedonor("False");
        return bloodDonationAppointment;
    }
}
