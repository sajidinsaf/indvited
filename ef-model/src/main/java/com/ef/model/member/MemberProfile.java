package com.ef.model.member;

import java.util.List;

import com.ef.model.core.Domain;
import com.ef.model.core.Forum;
import com.ef.model.core.Profile;

public class MemberProfile extends Profile {

  private static final long serialVersionUID = 6044555434967772456L;

  private final List<Domain> domains;
  private final List<Forum> forums;

  public MemberProfile(int id, String name, List<Domain> domains, List<Forum> forums) {
    super(id, name);
    this.domains = domains;
    this.forums = forums;

  }

  public List<Domain> getDomains() {
    return domains;
  }

  public List<Forum> getForums() {
    return forums;
  }

  @Override
  public String toString() {
    return super.toString() + "MemberProfile [domains=" + domains + ", forums=" + forums + "]";
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = super.hashCode();
    result = prime * result + ((domains == null) ? 0 : domains.hashCode());
    result = prime * result + ((forums == null) ? 0 : forums.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (!super.equals(obj))
      return false;
    if (getClass() != obj.getClass())
      return false;
    MemberProfile other = (MemberProfile) obj;
    if (domains == null) {
      if (other.domains != null)
        return false;
    } else if (!domains.equals(other.domains))
      return false;
    if (forums == null) {
      if (other.forums != null)
        return false;
    } else if (!forums.equals(other.forums))
      return false;
    return true;
  }

}
