package Qap2.Manager;
import Qap2.Domain.Appointment.AppointmentSlot;
import Qap2.Domain.Appointment.BloodDonationAppointment;
import Qap2.Domain.Donor.BloodDonor;
import Qap2.Domain.Database;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;

public class BloodDonationAppointmentManager {
    private Database database;

    public BloodDonationAppointmentManager(Database database) {
        this.database = database;
    }

    public BloodDonationAppointment bookAppointment(int bloodDonorId) throws InvalidDonationSchedulingException{
        BloodDonationAppointment bloodDonationAppointment = new BloodDonationAppointment();
        BloodDonor bloodDonor = database.getDonor();
        if (bloodDonor.getId() == bloodDonorId) {
            LocalDate dateofbirth = database.getDonor().getDateofbirth();
            LocalDate today = LocalDate.now();
            Period howold = Period.between(dateofbirth, today);
            int age = howold.getYears();
            if (age <= 18) {
                return null;
            } else if (age >= 80) {
                return null;
            }
        }
        if (bloodDonationAppointment.isFirsttimedonor() == "False") {
            if (bloodDonor.getId() == bloodDonorId) {
                LocalDate nextdate = database.getDonor().getNextappointment();
                LocalDate lastdate = database.getDonor().getLastdonationdate();
                Period differencewithdays = Period.between(nextdate, lastdate);
                int date = differencewithdays.getDays();
                if (date < 56) {
                    throw new InvalidDonationSchedulingException("Too soon");
                } else if (date > 365) {
                    throw new InvalidDonationSchedulingException("Too far");
                }
            }
        }
        List<AppointmentSlot> appointmentSlotList = database.getAppointmentSlots();
        for (AppointmentSlot appointmentSlot: appointmentSlotList) {
            if (appointmentSlot.getBloodtype().equalsIgnoreCase(bloodDonor.getBloodtype())) {
            } else {
                throw new InvalidDonationSchedulingException("invalid blood type");
            }
            if(appointmentSlot.equals(2)){
                throw new InvalidDonationSchedulingException("Appointment already made, no more then one");
            }
        }
        return bloodDonationAppointment;
    }
}
