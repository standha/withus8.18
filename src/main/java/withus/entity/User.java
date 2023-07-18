package withus.entity;

import lombok.*;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;

@Entity
@Table(indexes = @Index(columnList = "id,password"))
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@Setter
@ToString
public class User implements Serializable, UserDetails {
    @Id
    @Column(name = "id", columnDefinition = "VARCHAR(128) NOT NULL", length = 128, unique = true)
    @EqualsAndHashCode.Include
    @Getter
    private String userId;

    @Column(columnDefinition = "VARCHAR(255) NOT NULL")
    private String password;

    @Column(columnDefinition = "VARCHAR(32) NOT NULL", length = 32)
    @Getter
    private String name;

    @Column(columnDefinition = "TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP")
    @Builder.Default
    @Getter
    private LocalDateTime registrationDateTime = LocalDateTime.now();

    @Column(columnDefinition = "DATETIME")
    @Nullable
    @Getter
    @Setter
    private LocalDateTime userRecordDate;

    @Column(columnDefinition = "VARCHAR(32) NOT NULL", length = 32, unique = true)
    @NonNull
    @Getter
    private String contact;

    @Column( columnDefinition = "VARCHAR(32) NOT NULL",length = 32)
    @Nullable
    @Getter
    private String height;


    @Column(columnDefinition = "DATE")
    @Nullable
    @Getter
    private LocalDate birthdate;

    @Column(columnDefinition = "VARCHAR(6)", length = 6)
    @Enumerated(EnumType.STRING)
    @Nullable
    @Getter
    private Gender gender;

    @Column(columnDefinition = "VARCHAR(32)", length = 32)
    @Enumerated(EnumType.STRING)
    @Getter
    private Relative relative;

    @Column(columnDefinition = "VARCHAR(256)", length = 256)
    @Nullable
    @Getter
    private String appToken;

    @EqualsAndHashCode.Include
    @Column(columnDefinition = "VARCHAR(16) NOT NULL", length = 16)
    @Enumerated(EnumType.STRING)
    @Getter
    private Type type;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "caregiver_contact", columnDefinition = "VARCHAR(32)", referencedColumnName = "contact")
    @Nullable
    @Getter
    private User caregiver;

    @Column(name = "week")
    @Getter
    private Integer week;

    @Column(name = "level")
    @Getter
    private Integer level;

    @Column(name="temp_contact", columnDefinition = "VARCHAR(32)")
    @Nullable
    @Getter
    private String tempContact;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return new ArrayList<>();
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return userId;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public enum Gender {
        MALE, FEMALE;

        public static Gender byName(String name) {
            for (Gender gender : values()) {
                if (gender.name().equals(name)) {
                    return gender;
                }
            }

            return null;
        }
    }

    public enum Relative {
        NONE, SPOUSE, CHILD, RELATIVE, ETC;


        public static Relative RbyName(String name) {
            for (Relative relative : values()) {
                if (relative.name().equalsIgnoreCase(name)) {
                    return relative;
                }
            }

            return null;
        }
    }

    public enum Type {
        PATIENT, CAREGIVER, ADMINISTRATOR
    }
}