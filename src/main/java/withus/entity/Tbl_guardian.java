package withus.entity;


import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "tbl_guardian", uniqueConstraints = {@UniqueConstraint(columnNames = "gtell")})
public class Tbl_guardian implements Serializable, UserDetails {

    @Id
    @Column(name = "registration_count" , columnDefinition = "INTEGER(11)")
    private int registrationCount;

    @Column(name = "guardian", columnDefinition = "VARCHAR(20)")
    private String guardianId; //Unique

    @Column(name = "gpassword" , columnDefinition = "VARCHAR(20)")
    private String gpassword;

    @Column(name = "gname", columnDefinition = "VARCHAR(20)")
    private String gname;

    @Column(name =  "gtell", columnDefinition = "VARCHAR(20)")
    private String gtell;

    @Column(name = "linked_Patient_Id", columnDefinition = "VARCHAR(20)")
    private String linkedPatientId;

    @Column(name = "gapp_Token", columnDefinition = "VARCHAR(256)")
    private String gappToken;

    public Tbl_guardian() {}
    private Tbl_guardian(Integer registrationCount, @Nonnull String guardianId, String gpassword, String gname,
                         String gtell, String linkedPatientId, String gappToken){
        this.registrationCount = registrationCount;
        this.guardianId = guardianId;
        this.gpassword = gpassword;
        this.gname = gname;
        this.gtell = gtell;
        this.linkedPatientId = linkedPatientId;
        this.gappToken = gappToken;
    }

    @Override
    public boolean equals(Object o){
        if(this == o){return true;}
        if(o == null || getClass() != o.getClass()) {return false;}

        Tbl_guardian tbl_guardian = (Tbl_guardian)o;
        return guardianId.equals(tbl_guardian.guardianId);
    }

    @Override
    public int hashCode(){
        return Objects.hash(guardianId);
    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities(){
        return new ArrayList<>();
    }
    @Override
    public String getPassword(){return gpassword;}
    @Override
    public String getUsername(){return guardianId;}
    @Nonnull
    public String getGuardianId() {return guardianId;}
    @Nonnull
   public Integer getRegistrationCount() {return registrationCount;}
    public String getName() {return gname;}
    @Nonnull
    public String getGtell() {return gtell;}
    @Nonnull
    public String getLinkedPatientId() {return linkedPatientId;}
    @Nullable
    public String getGappToken() {return gappToken;}

    @Override
    public boolean isAccountNonExpired() {return true;}
    @Override
    public boolean isAccountNonLocked() { return true; }
    @Override
    public boolean isCredentialsNonExpired() { return true; }
    @Override
    public boolean isEnabled() { return true; }
    public void setLinkedPatientId(String linked_user_id){
        this.linkedPatientId = linked_user_id;
    }
    public void setRegistrationCount(Integer registrationCount){
        this.registrationCount = registrationCount;
    }
    public void setGuardianId(String guardian){
        this.guardianId = guardian;
    }
    public void setGname(String gname){
        this.gname = gname;
    }
    public void setGpassword(String gpassword){
        this.gpassword = gpassword;
    }
    public void setG_tell(String gtell){
        this.gtell = gtell;
    }

    public static class Builder{
        public Builder setRegistrationCount(Integer registrationCount) {
            this.registrationCount = registrationCount;   return this;
        }

        public Builder setId(String guardian) {
            this.guardianId = guardian;   return this;
        }

        public Builder setPassword(String gpassword) {
            this.gpassword = gpassword;   return this;
        }

        public Builder setName(String gname) {
            this.gname = gname;   return this;
        }

        public Builder setLinkedPatientId(String linkedPatientId) {
            this.linkedPatientId = linkedPatientId;   return this;
        }

        public Builder setGappToken(String gappToken) {
            this.gappToken = gappToken;   return this;
        }
        public Builder setGtell(String gtell){
            this.gtell = gtell; return this;
        }

        private Integer registrationCount;
        private String guardianId;
        private String gpassword;
        private String gname;
        private String gtell;
        private String linkedPatientId;
        private String gappToken;

        public Tbl_guardian createGuardian() {
            return new Tbl_guardian(registrationCount, guardianId, gpassword, gname, gtell, linkedPatientId, gappToken);
        }
    }
    public static Builder builder(){
        return new Builder();
    }

}
