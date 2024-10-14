
package app.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor

public class GuestDto {
   private long id;
   private UserDto userId;
   private PartnerDto partnerId;
   private String status;
}
