package com.trust.amanat.common.constants;

public class AppConstants {

    // Roles
    public static class Role {
        public static final String ADMIN = "ADMIN";
        public static final String USER = "USER";
    }

    //  MESSAGES
    public static class Message {

        // General
        public static final String INVALID_CREDENTIALS = "Invalid credentials, Try again with correct credential";
        public static final String SOMETHING_WRONG = "Something went wrong";
        public static final String SOMETHING_WENT_ERROR = "Something went error";
        public static final String DATABASE_ERROR = "Database error occurred";

        // Common Status
        public static final String SUCCESS = "SUCCESS";
        public static final String FAILED = "Failed";
        public static final String SAVED_FAILED = "Saved Failed";
        public static final String UPDATED = "Updated Successfully";
        public static final String DELETED_SUCCESSFULLY = "Deleted successfully";
        public static final String RESIGNED = "RESIGNED";

        public static final String ACCEPTED = "ACCEPTED";

        public static final String BENEFICIARY_DOCUMENTS = "beneficiary-documents";
        public static final String PROFILE_PIC = "profile-pictures";





        // Admin
        public static final String ADMIN_ID_ALREADY_EXISTS = "Admin Id already exists";
        public static final String ADMIN_ID = "Your Admin Id is: ";
        public static final String SUPER_ADMIN =   "SUPER_ADMIN";
        public static final String ADMIN_CREATED = "Admin created successfully";
        public static final String ADMIN_CREATION_FAILED = "Failed to create admin";
        public static final String ADMIN_UPDATED = "Admin updated successfully";
        public static final String ADMIN_UPDATE_FAILED = "Failed to update admin";

        // Beneficiary
        public static final String BENEFICIARY_CREATED = "Your details submitted successfully";
        public static final String BENEFICIARY_FAILED = "Something went wrong, Please try again";
        public static final String BENEFICIARY_UPDATED = "Updated Successfully";
        public static final String BENEFICIARY_UPDATE_FAILED = "Update Failed";
        public static final String SUCCESSFUL_ASKING_REQUEST = "Thank you for Asking Request, Your HelpRequest Token No is: ";
        public static final String WHATSAPP_AND_EMAIL_CONTACT_MSG = " For further update Contact us on WhatsApp or email";
        public static final String WHATSAPP= " WhatsApp No: 7277222729 ";
        public static final String GMAIL= " Email: amanatwelfaretrust@gmail.com";


        // Member
        public static final String MEMBER_ADDED = "Member added successfully";
        public static final String MEMBER_ADDING_FAILED = "Failed to add member";
        public static final String MEMBER_NOT_FOUND = "Member not found";
        public static final String MEMBER_UPDATED = "Member updated successfully";

        // Post Holder
        public static final String POST_HOLDER_NOT_FOUND = "Post holder not found with id: ";
        public static final String POST_HOLDER_DELETED_SUCCESSFULLY = "Post holder deleted successfully";
        public static final String POST_HOLDER_ADDED_SUCCESSFULLY = "Post holder added successfully";

        // Expenditure
        public static final String EXP_ADDED_SUCCESSFULLY = "Expenditure added successfully";

        // Payment
        public static final String PAYMENT_ADDED = "Payment added successfully";
        public static final String PAYMENT_DETAILS_SUBMITTED = "Payment details submitted Successfully";
        public static final String PAYMENT_FAILED = "Something went wrong while adding payment";

        // Auth
        public static final String USER_NOT_AUTHENTICATED = "User not authenticated";

        // Profile
        public static final String PROFILE_PIC_REMOVED = "Profile picture removed successfully";
        public static final String FAILED_TO_UPLOAD_PROFILE_PIC = "Failed to upload profile picture";

        // Status
        public static final String ACTIVE = "ACTIVE";
        public static final String INACTIVE = "INACTIVE";
        public static final String Flag_APPROVED = "A";
        public static final String Flag_PENDING = "P";
        public static final String Flag_REJECT = "R";
        public static final String PENDING = "PENDING";
        public static final String REJECTED = "REJECTED";
        public static final String WORKING = "WORKING";

        

        // Receipt
        public static final String RECEIPT_ALREADY_GENERATED = "Receipt already generated";
        public static final String RECEIPT_NOT_FOUND = "Receipt not found";

        // PDF Labels
        public static final String AMANAT_WELFARE_TRUST = "AMANAT WELFARE TRUST";
        public static final String RECEIPT = "Receipt";
        public static final String LABEL_RECEIPT_NO = "Receipt No : ";
        public static final String LABEL_NAME = "Name : ";
        public static final String LABEL_ADDRESS = "Address : ";
        public static final String LABEL_AMOUNT = "Amount : ";
        public static final String LABEL_RECEIPT_DATE = "Receipt Date : ";
        public static final String AWT = "AWT";

        // Report Keys
        public static final String YEAR = "year";
        public static final String INCOME = "income";
        public static final String EXPENSE = "expense";
        public static final String LEFT_BALANCE = "left";

        // Security
        public static final String AUTH_HEADER = "Authorization";
        public static final String BEARER_PREFIX = "Bearer ";

        public static final String PENDING_APPROVAL ="Your request is pending for approval";
        public static final String REJECTED_REQUEST ="Sorry!, Your Signup request is Rejected";
        public static final String ACCESS_DENIED ="Access Denied";

        //Feedback

        public static final String FEEDBACK_RECEIVED = "Thanks for your feedback, Your Feedback sent to AWT Management";

        //RESIGNATION
        public static final String RESIGNATION_SENT = "Resignation Sent";
        public static final String RESIGNATION_ACCEPTED = "Resignation Accepted";


    }

    //  VALIDATION
    public static class Validation {

        public static final String USERNAME_REQUIRED = "User Name is required";
        public static final String NAME_REQUIRED = "Name is required";
        public static final String NEW_PASSWORD_REQUIRED = "New Password is required";
        public static final String ADDRESS_REQUIRED = "Address is required";
        public static final String PASSWORD_RESET_SUCCESS = "Password reset successfully";
        public static final String PASSWORD_RESET_FAILED = "Failed to reset password";
        public static final String CURRENT_PASSWORD_REQUIRED = "Current Password is required";
        public static final String USER_NOT_AUTHENTICATED = "User not authenticated";
        public static final String CURRENT_PASSWORD_INCORRECT = "Current password is incorrect";
        public static final String PASSWORD_CHANGE_SUCCESS = "Password changed successfully";
        public static final String PASSWORD_CHANGE_FAILED = "Failed to change password";
        public static final String CURRENT_PASSWORD_INCORRCT = "Current password is incorrect";

        public static final String AMOUNT_REQUIRED = "Amount is required";
        public static final String MEMBER_ID_DOES_NOT_EXISTS = "Member Id Does Not Exist";
        public static final String EXP_DATE_REQUIRED = "Expenditure date is required";
        public static final String YEAR_REQUIRED = "Year is required";
        public static final String RECEIPT_REQUIRED = "Receipt number is required";
        public static final String EXP_DATA_REQUIRED = "Expenditure data is required";
        public static final String PRB_DET_REQUIRED = "Problem detail is required";
        public static final String SUP_DOC_REQUIRED = "Supportive Docs is required";

        public static final String REQUEST_BODY_EMPTY = "Request body is empty";
        public static final String MEMBER_ID_REQUIRED = "Member Id is required";
        public static final String AMOUNT_NOT_GREATER_ZERO = "Amount must be greater than zero";
        public static final String MONTH_REQUIRED = "Month is required";

        public static final String USERNAME_ALREADY_EXISTS = "This username is already exists";
        public static final String EMAIL_ALREADY_EXISTS = "This email is already exists";
        public static final String MOBILE_ALREADY_EXISTS = "This Mobile no is already exists";
        public static final String MOBILE_NO_ALREADY_EXISTS = "Mobile Number already exists";
        public static final String USER_NOT_FOUND = "User not found";
        public static final String USER_NOT_FOUND_WITH_THIS_ID = "User not found with this id: ";

        public static final String MOBILE_ALREADY_EXISTS_TO_ANOTHER = "Mobile already exists to another one";
        public static final String EMAIL_ALREADY_EXISTS_TO_ANOTHER = "Email already exists to another one";

        public static final String MULTIPLE_RECORD_USER = "This username has multiple records, Please SignUp with new username.";
    }
}