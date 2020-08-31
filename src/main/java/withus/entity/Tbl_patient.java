
package withus.entity;



import org.springframework.lang.NonNull;
import javax.annotation.Nonnull;
import javax.persistence.*;
import org.springframework.lang.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "tbl_patient", uniqueConstraints = {@UniqueConstraint(columnNames = {"gcontact","contact","patient"})})
public class Tbl_patient implements Serializable, UserDetails {
   // 테이블 , 칼럼의 name 매핑시 빨간 에러줄이 뜨는데 신경 안써두됨

   @Column(name = "patient", columnDefinition = "VARCHAR(128)", length = 128)
   @NonNull
   protected String patientId;

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   @Column(name = "registration_Count")
   private Integer registrationCount;

   @Column(name = "password" ,columnDefinition = "VARCHAR(20)")
   private String password;

   @Column(name = "name", columnDefinition = "VARCHAR(20)")
   private String name;

   @Column(name = "birthday", columnDefinition = "DATE")
   private LocalDate birthday;

   @Column(name = "gender", columnDefinition = "VARCHAR(8)")
   @Enumerated(EnumType.STRING)
   private Gender gender;

   @Column(name = "contact" , columnDefinition = "VARCHAR(20)")
   private String contact;

   @Column(name = "gcontact", columnDefinition = "VARCHAR(20)")
   private String gcontact;

   @Column(name = "app_Token", columnDefinition = "VARCHAR(256)")
   private String appToken;


   public Tbl_patient() {}
   private Tbl_patient(Integer registrationCount, @NonNull String patientId, String password, String name, LocalDate birthday,
                       Gender gender, String contact, String gcontact, String appToken){

      this.registrationCount = registrationCount;
      this.patientId = patientId;
      this.password = password;
      this.name=name;
      this.contact = contact;
      this.gcontact = gcontact;
      this.birthday = birthday;
      this.gender = gender;
      this.gcontact = gcontact;
      this.appToken = appToken;

   }

   @Override
   public boolean equals(Object o){
      if(this == o){return true;}
      if(o == null || getClass() != o.getClass()) {return false;}

      Tbl_patient tbl_patient = (Tbl_patient)o;
      return patientId.equals(tbl_patient.patientId);
   }
   @Override
   public int hashCode(){
      return Objects.hash(patientId);
   }
   @Override
   public Collection<? extends  GrantedAuthority> getAuthorities(){
      return new ArrayList<>();
   }
   @Override
   public String getPassword(){return password;}
   @Override
   public String getUsername(){return patientId;}
   @Nonnull
   public String getPatientId() {return patientId;}
   public Integer getRegistrationCount() {return registrationCount;}
   public String getName() {return name;}
   @Nonnull
   public String getContact() {return contact;}
   @Nonnull
   public String getGcontact() {return gcontact;}
   @Nullable
   public Gender getGender() {return gender;}
   @Nullable
   public LocalDate getBirthday() {return birthday;}
   @Nullable
   public String getAppToken() {return appToken;}


   @Override
   public boolean isAccountNonExpired() {return true;}
   @Override
   public boolean isAccountNonLocked() { return true; }
   @Override
   public boolean isCredentialsNonExpired() { return true; }
   @Override
   public boolean isEnabled() { return true; }

   public void setPassword(String password) {
      this.password = password;
   }
   public void setGcontact(String gcontact) {
      this.gcontact = gcontact;
   }
   public enum Gender{
      MALE,
      FEAMLE
   }

   public static class Builder{
      private Integer registrationCount;
      private String name;
      private String patient;
      private String password;
      private String contact;
      private String gcontact;
      private LocalDate birthday;
      private Gender gender;
      private String appToken;


      public Builder setRegistrationCount(Integer registrationCount) {
         this.registrationCount = registrationCount;
         return this;
      }
      public Builder setName(String name){
         this.name = name; return this;
      }
      public Builder setId(String patient) {
         this.patient = patient; return this;
      }

      public Builder setPassword(String password) {
         this.password = password; return this;
      }

      public Builder setContact(String contact) {
         this.contact = contact; return this;
      }

      public Builder setGcontact(String gcontact) {
         this.gcontact = gcontact; return this;
      }

      public Builder setBirthday(LocalDate birthday) {
         this.birthday = birthday; return this;
      }

      public Builder setGender(Gender gender) {
         this.gender = gender; return this;
      }

      public Builder setAppToken(String appToken) {
         this.appToken = appToken; return this;
      }
      public Tbl_patient createPatient() {
         return new Tbl_patient(registrationCount, patient, password, name, birthday, gender, contact, gcontact, appToken);
      }

   }
   public static Builder builder(){
      return new Builder();
   }

}
