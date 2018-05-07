package com.tuinercia.inercia.interfaces;

import com.tuinercia.inercia.DTO.Membership;
import com.tuinercia.inercia.DTO.MembershipProgress;

/**
 * Created by ricar on 28/03/2018.
 */

public interface InerciaApiGetCurrentMembershipListener {
    void onErrorServer(int statusCode);
    void onGetCurrentMembershipSuccess(Membership membership, MembershipProgress membershipProgress, boolean showProgress);
    void onGetCurrentMembershipError(String errorMessage);
}
