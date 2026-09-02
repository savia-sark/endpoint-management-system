package com.example.iaccs.security;

import com.example.iaccs.entity.Agency;
import com.example.iaccs.entity.Endpoint;
import com.example.iaccs.entity.Ntn;
import com.example.iaccs.entity.User;
import com.example.iaccs.service.AgencyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

@Service
public class AccessControlService {

    private final SecurityUtils securityUtils;


    public AccessControlService(SecurityUtils securityUtils) {
        this.securityUtils = securityUtils;
    }

    public void checkCanCreateEndpoint(Agency agency) {

        User user = securityUtils.getCurrentUser();

        switch (user.getRole()) {

            case SUPER_ADMIN:
            case ADMIN:
                return;

            case COMMAND_HEAD:
                if (!agency.getNtn()
                        .getCommand()
                        .getCommandId()
                        .equals(user.getCommand().getCommandId())) {

                    throw new AccessDeniedException(
                            "Cannot create endpoint outside your Command"
                    );
                }
                return;


            case NTN_HEAD:
                if (!agency.getNtn()
                        .getNtnId()
                        .equals(user.getNtn()
                                .getNtnId())) {

                    throw new AccessDeniedException(
                            "Cannot create endpoint outside your NTN"
                    );
                }
                return;


            case AGENCY_HEAD:
                if (!agency.getAgencyId()
                        .equals(user.getAgency().getAgencyId())) {

                    throw new AccessDeniedException(
                            "Cannot create endpoint outside your agency"
                    );
                }
                return;


            default:
                throw new AccessDeniedException("Access denied");
        }
    }
    public void checkCanAccessEndpoint( Endpoint endpoint) {

        User currentUser = securityUtils.getCurrentUser();

        switch (currentUser.getRole()) {

            case SUPER_ADMIN:
            case ADMIN:
                return;

            case COMMAND_HEAD:
                if (!endpoint.getAgency()
                        .getNtn()
                        .getCommand()
                        .getCommandId()
                        .equals(currentUser.getCommand().getCommandId())) {
                    throw new AccessDeniedException("Access denied");
                }
                return;

            case NTN_HEAD:
                if (!endpoint.getAgency()
                        .getNtn()
                        .getNtnId()
                        .equals(currentUser.getNtn().getNtnId())) {
                    throw new AccessDeniedException("Access denied");
                }
                return;

            case AGENCY_HEAD:
                if (!endpoint.getAgency()
                        .getAgencyId()
                        .equals(currentUser.getAgency().getAgencyId())) {
                    throw new AccessDeniedException("Access denied");
                }
                return;

            default:
                throw new AccessDeniedException("Access denied");
        }
    }

    public void checkCanUpdateEndpoint(Endpoint endpoint) {
        checkCanAccessEndpoint(endpoint);
    }

    public void checkCanDeleteEndpoint(Endpoint endpoint) {
        checkCanAccessEndpoint(endpoint);
    }

    public void checkCanAccessAgency(Agency agency) {
        User currentUser = securityUtils.getCurrentUser();

        switch (currentUser.getRole()) {

            case SUPER_ADMIN:
            case ADMIN:
                return;

            case COMMAND_HEAD:
                if (!agency.getNtn()
                        .getCommand()
                        .getCommandId()
                        .equals(currentUser.getCommand().getCommandId())) {
                    System.out.println("cannot Access Agency");
                    throw new AccessDeniedException("Access denied");
                }
                return;

            case NTN_HEAD:
                if (!agency.getNtn()
                        .getNtnId()
                        .equals(currentUser.getNtn().getNtnId())) {
                    System.out.println("cannot Access Agency");
                    throw new AccessDeniedException("Access denied");
                }
                return;

            case AGENCY_HEAD:
                if (!agency.getAgencyId()
                        .equals(currentUser.getAgency().getAgencyId())) {
                    System.out.println("cannot Access Agency");
                    throw new AccessDeniedException("Access denied");
                }
                return;

            default:
                throw new AccessDeniedException("Access denied");
        }
    }

    public void checkCanCreateAgency(Ntn ntn) {
        User user = securityUtils.getCurrentUser();

        switch (user.getRole()) {

            case SUPER_ADMIN:
            case ADMIN:
                return;

            case COMMAND_HEAD:
                if (!ntn.getCommand()
                        .getCommandId()
                        .equals(user.getCommand().getCommandId())) {
                    throw new AccessDeniedException(
                            "Cannot create endpoint outside your Command"
                    );
                }
                return;


            case NTN_HEAD:
                if (!ntn.getNtnId()
                        .equals(user.getNtn()
                                .getNtnId())) {
                    throw new AccessDeniedException(
                            "Cannot create endpoint outside your NTN"
                    );
                }
                return;


            default:
                throw new AccessDeniedException("Access denied");
        }
    }

    public void checkCanUpdateAgency(Agency agency) {
        checkCanAccessAgency(agency);
    }

    public void checkCanDeleteAgency(Agency agency) {
        checkCanAccessAgency(agency);
    }


    public void checkCanAccessAgencyType(){
        User user = securityUtils.getCurrentUser();
        switch (user.getRole()) {
            case SUPER_ADMIN:

            case ADMIN:
                return;
            default:
                System.out.println("cannot create Agency");
                throw new AccessDeniedException("Access denied");
        }
    }
    public void checkCanCreateAgencyType() {
        checkCanAccessAgencyType();
    }
    public void checkCanUpdateAgencyType() {
        checkCanAccessAgencyType();
    }

    public void checkCanDeleteAgencyType() {
        checkCanAccessAgencyType();
    }
}