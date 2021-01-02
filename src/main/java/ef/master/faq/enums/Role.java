package ef.master.faq.enums;

public enum Role {
  STUDENT,
  PROFESSOR,
  OFFICE;

  public String getRole() {
    return "ROLE_" + this.name();
  }
}
