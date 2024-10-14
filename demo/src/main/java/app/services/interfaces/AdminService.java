package app.services.interfaces;

import app.dto.PartnerDto;
import app.dto.UserDto;

public interface AdminService {
    public void viewInvoiceHistory(UserDto userDto) throws Exception;
    public void createPartner(PartnerDto partner) throws Exception;
    public void vipPromotion() throws Exception;
}
