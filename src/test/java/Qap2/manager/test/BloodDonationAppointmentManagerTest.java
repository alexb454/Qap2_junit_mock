package Qap2.manager.test;

import Qap2.Domain.Appointment.BloodDonationAppointment;
import Qap2.Domain.Appointment.AppointmentSlot;
import Qap2.Domain.Database;
import Qap2.Domain.Donor.BloodDonor;
import Qap2.Manager.BloodDonationAppointmentManager;
import Qap2.Manager.InvalidDonationSchedulingException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Month;
import java.util.ArrayList;

@ExtendWith(MockitoExtension.class)
public class BloodDonationAppointmentManagerTest {

    @Mock
    private Database databaseMock;

    @Test
    public void ProperDonationNoIssues() {
        BloodDonor ProperDonation = new BloodDonor();
        ProperDonation.setId(1);
        ProperDonation.setBloodtype("A");
        ProperDonation.setFirstname("Alex");
        ProperDonation.setLastname("Bristow");
        ProperDonation.setDateofbirth(LocalDate.of(1994, Month.MAY, 04));
        Mockito.when(databaseMock.getDonor()).thenReturn(ProperDonation);

        BloodDonationAppointment BloodDonation = new BloodDonationAppointment();
        BloodDonation.setAppointmentduration(LocalTime.of(12, 0));
        BloodDonation.setFirsttimedonor("True");

        ArrayList<AppointmentSlot> appointmentSlots = new ArrayList<AppointmentSlot>();
        AppointmentSlot appointmentSlot = new AppointmentSlot();
        appointmentSlot.setId(1);
        appointmentSlot.setLocation("83 Blue St. st. John's NL");
        appointmentSlot.setBloodtype("A");
        appointmentSlots.add(appointmentSlot);
        Mockito.when(databaseMock.getAppointmentSlots()).thenReturn(appointmentSlots);
        BloodDonationAppointmentManager bloodDonationAppointmentManager = new BloodDonationAppointmentManager(databaseMock);
        try{
            BloodDonationAppointment bloodDonationAppointment = bloodDonationAppointmentManager.bookAppointment(1);
        } catch(InvalidDonationSchedulingException e){
            Assertions.assertTrue(e.getMessage().equalsIgnoreCase("Didnt Make Donation Date"));
        }
    }

    @Test
    public void BloodTypeAreTheSame() {
        BloodDonor ABloodDonation = new BloodDonor();
        ABloodDonation.setId(1);
        ABloodDonation.setBloodtype("A");
        ABloodDonation.setFirstname("A");
        ABloodDonation.setLastname("Wayne");
        ABloodDonation.setDateofbirth(LocalDate.of(2000, Month.APRIL, 25));
        Mockito.when(databaseMock.getDonor()).thenReturn(ABloodDonation);

        ArrayList<AppointmentSlot> appointmentSlots = new ArrayList<AppointmentSlot>();
        AppointmentSlot appointmentSlot = new AppointmentSlot();
        appointmentSlot.setId(1);
        appointmentSlot.setLocation("83 Blue St. st. John's NL");
        appointmentSlot.setBloodtype("A");
        appointmentSlots.add(appointmentSlot);
        Mockito.when(databaseMock.getAppointmentSlots()).thenReturn(appointmentSlots);
        BloodDonationAppointmentManager bloodDonationAppointmentManager = new BloodDonationAppointmentManager(databaseMock);
        try{
            BloodDonationAppointment bloodDonationAppointment = bloodDonationAppointmentManager.bookAppointment(1);
        } catch(InvalidDonationSchedulingException e){
            Assertions.assertTrue(e.getMessage().equalsIgnoreCase("invalid blood type"));
        }
    }

    @Test
    public void TooYoungTest() {
        BloodDonor tooYoungForBloodDonation = new BloodDonor();
        tooYoungForBloodDonation.setId(1);
        tooYoungForBloodDonation.setFirstname("Lil");
        tooYoungForBloodDonation.setLastname("Wayne");
        tooYoungForBloodDonation.setDateofbirth(LocalDate.of(2014, Month.APRIL, 25));
        Mockito.when(databaseMock.getDonor()).thenReturn(tooYoungForBloodDonation);
        BloodDonationAppointmentManager bloodDonationAppointmentManager = new BloodDonationAppointmentManager(databaseMock);
        try{
            BloodDonationAppointment bloodDonationAppointment = bloodDonationAppointmentManager.bookAppointment(1);
        }catch(InvalidDonationSchedulingException e){
            Assertions.assertTrue(e.getMessage().equalsIgnoreCase("donor too young!"));
        }
    }

    @Test
    public void TooOldTest() {
        BloodDonor tooOldForBloodDonation = new BloodDonor();
        tooOldForBloodDonation.setId(1);
        tooOldForBloodDonation.setFirstname("Old");
        tooOldForBloodDonation.setLastname("Wayne");
        tooOldForBloodDonation.setDateofbirth(LocalDate.of(1914, Month.APRIL, 25));
        Mockito.when(databaseMock.getDonor()).thenReturn(tooOldForBloodDonation);
        BloodDonationAppointmentManager bloodDonationAppointmentManager = new BloodDonationAppointmentManager(databaseMock);
        try{
            BloodDonationAppointment bloodDonationAppointment = bloodDonationAppointmentManager.bookAppointment(1);
        }catch(InvalidDonationSchedulingException e){
            Assertions.assertTrue(e.getMessage().equalsIgnoreCase("donor too old!"));
        }
    }

    @Test
    public void BloodTypeNotTheSame() {
        BloodDonor ABloodDonation = new BloodDonor();
        ABloodDonation.setId(1);
        ABloodDonation.setBloodtype("A");
        ABloodDonation.setFirstname("A");
        ABloodDonation.setLastname("Wayne");
        ABloodDonation.setDateofbirth(LocalDate.of(1984, Month.APRIL, 25));
        Mockito.when(databaseMock.getDonor()).thenReturn(ABloodDonation);

        ArrayList<AppointmentSlot> appointmentSlots = new ArrayList<AppointmentSlot>();
        AppointmentSlot appointmentSlot = new AppointmentSlot();
        appointmentSlot.setId(1);
        appointmentSlot.setLocation("83 Blue St. st. John's NL");
        appointmentSlot.setBloodtype("B");
        appointmentSlots.add(appointmentSlot);
        Mockito.when(databaseMock.getAppointmentSlots()).thenReturn(appointmentSlots);
        BloodDonationAppointmentManager bloodDonationAppointmentManager = new BloodDonationAppointmentManager(databaseMock);
        try{
            BloodDonationAppointment bloodDonationAppointment = bloodDonationAppointmentManager.bookAppointment(1);
        } catch(InvalidDonationSchedulingException e){
            Assertions.assertTrue(e.getMessage().equalsIgnoreCase("invalid blood type"));
        }
    }

    @Test
    public void TooSoonFromLastAppointment(){
        BloodDonor TooSoonDonation = new BloodDonor();
        TooSoonDonation.setId(1);
        TooSoonDonation.setBloodtype("A");
        TooSoonDonation.setFirstname("Alex");
        TooSoonDonation.setLastname("Bristow");
        TooSoonDonation.setDateofbirth(LocalDate.of(1994, Month.MAY, 04));
        TooSoonDonation.setLastdonationdate(LocalDate.of(2021, Month.AUGUST, 20));
        TooSoonDonation.setNextappointment(LocalDate.of(2021, Month.AUGUST, 24));
        Mockito.when(databaseMock.getDonor()).thenReturn(TooSoonDonation);

        BloodDonationAppointment BloodDonation = new BloodDonationAppointment();
        BloodDonation.setAppointmentduration(LocalTime.of(12, 0));
        BloodDonation.setFirsttimedonor("False");

        BloodDonationAppointmentManager bloodDonationAppointmentManager = new BloodDonationAppointmentManager(databaseMock);
        try{
            BloodDonationAppointment bloodDonationAppointment = bloodDonationAppointmentManager.bookAppointment(1);
        }catch(InvalidDonationSchedulingException e){
            Assertions.assertTrue(e.getMessage().equalsIgnoreCase("Too soon"));
        }
    }

    @Test
    public void TooFarFromLastAppointment(){
        BloodDonor TooFarDonation = new BloodDonor();
        TooFarDonation.setId(1);
        TooFarDonation.setBloodtype("A");
        TooFarDonation.setFirstname("Alex");
        TooFarDonation.setLastname("Bristow");
        TooFarDonation.setDateofbirth(LocalDate.of(1994, Month.MAY, 04));
        TooFarDonation.setLastdonationdate(LocalDate.of(2021, Month.AUGUST, 20));
        TooFarDonation.setNextappointment(LocalDate.of(2023, Month.AUGUST, 24));
        Mockito.when(databaseMock.getDonor()).thenReturn(TooFarDonation);

        BloodDonationAppointment BloodDonation = new BloodDonationAppointment();
        BloodDonation.setAppointmentduration(LocalTime.of(12, 0));
        BloodDonation.setFirsttimedonor("False");

        BloodDonationAppointmentManager bloodDonationAppointmentManager = new BloodDonationAppointmentManager(databaseMock);
        try{
            BloodDonationAppointment bloodDonationAppointment = bloodDonationAppointmentManager.bookAppointment(1);
        }catch(InvalidDonationSchedulingException e){
            Assertions.assertTrue(e.getMessage().equalsIgnoreCase("Too far"));
        }
    }

    @Test
    public void TooManyAppointments() {
        BloodDonor TooManyAppointments = new BloodDonor();
        TooManyAppointments.setId(1);
        TooManyAppointments.setBloodtype("A");
        TooManyAppointments.setFirstname("Alex");
        TooManyAppointments.setLastname("Bristow");
        TooManyAppointments.setDateofbirth(LocalDate.of(1994, Month.MAY, 04));
        TooManyAppointments.setLastdonationdate(LocalDate.of(2021, Month.AUGUST, 20));
        TooManyAppointments.setNextappointment(LocalDate.of(2022, Month.JANUARY, 24));
        Mockito.when(databaseMock.getDonor()).thenReturn(TooManyAppointments);

        BloodDonationAppointment BloodDonation = new BloodDonationAppointment();
        BloodDonation.setAppointmentduration(LocalTime.of(12, 0));
        BloodDonation.setFirsttimedonor("False");

        ArrayList<AppointmentSlot> appointmentSlots1 = new ArrayList<AppointmentSlot>();
        AppointmentSlot appointmentSlot = new AppointmentSlot();
        appointmentSlot.setId(1);
        appointmentSlot.setLocation("83 Blue St. st. John's NL");
        appointmentSlot.setBloodtype("A");
        appointmentSlots1.add(appointmentSlot);

        ArrayList<AppointmentSlot> appointmentSlots2 = new ArrayList<AppointmentSlot>();
        AppointmentSlot appointmentSlot2 = new AppointmentSlot();
        appointmentSlot2.setId(1);
        appointmentSlot2.setLocation("83 Blue St. st. John's NL");
        appointmentSlot2.setBloodtype("A");
        appointmentSlots2.add(appointmentSlot);
        Mockito.when(databaseMock.getAppointmentSlots()).thenReturn(appointmentSlots2);
        BloodDonationAppointmentManager bloodDonationAppointmentManager = new BloodDonationAppointmentManager(databaseMock);
        try{
            BloodDonationAppointment bloodDonationAppointment = bloodDonationAppointmentManager.bookAppointment(1);
        } catch(InvalidDonationSchedulingException e){
            Assertions.assertTrue(e.getMessage().equalsIgnoreCase("Appointment already made, no more then one"));
        }
    }
}
