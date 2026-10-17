package org.airtribe.AuthenticationAuthorizationBELC22.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import java.util.Date;


@Entity
public class VerificationToken {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private long id;

  private String token;

  private Date expiryAt;

  @OneToOne
  private User user;

  public VerificationToken(long id, String token, Date expiryAt, User user) {
    this.id = id;
    this.token = token;
    this.expiryAt = expiryAt;
    this.user = user;
  }

  public VerificationToken() {

  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public String getToken() {
    return token;
  }

  public void setToken(String token) {
    this.token = token;
  }

  public Date getExpiryAt() {
    return expiryAt;
  }

  public void setExpiryAt(Date expiryAt) {
    this.expiryAt = expiryAt;
  }

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }
}

// token -> signed -> econded
