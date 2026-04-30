package com.trust.amanat.common.constants;

public class AppConstants {

    // Roles
    public static class Role {
        public static final String ADMIN = "ADMIN";
        public static final String USER = "USER";
    }

    // Messages
    public static class Message {
        public static final String INVALID_CREDENTIALS = "Invalid credentials, Try again with correct credential";
        public static final String SOMETHING_WRONG = "Something went wrong";
        public static final String SOMETHING_WENT_ERROR = "Something went error";

        public static final String ADMIN_ID = "Your Admin Id is: ";
        // Beneficiary
        public static final String BENEFICIARY_CREATED = "Your details submitted successfully";
        public static final String BENEFICIARY_FAILED = "Something went wrong, Please try again";
        public static final String BENEFICIARY_UPDATED = "Updated Successfully";
        public static final String BENEFICIARY_UPDATE_FAILED = "Update Failed";
        public static final String MEMBER_ADDED = "Member added successfully";
        public static final String MEMBER_ADDING_FAILED = "Failed to add member";
        public static final String MEMBER_UPDATED ="Member updated successfully";
        public static final String RECEIPT_ALREADY_GENERATED = "Receipt already generated";
        public static final String RECEIPT_NOT_FOUND ="Receipt not found";
        public static final String POST_HOLDER_NOT_FOUND ="Post holder not found with id: ";
        public static final String POST_HOLDER_DELETED_SUCCESSFULLY ="Post holder deleted successfully";
        public static final String POST_HOLDER_ADDED_SUCCESSFULLY ="Post holder added successfully";

        // Common
        public static final String SUCCESS = "SUCCESS";
        public static final String FAILED = "Failed";
        public static final String SAVED_FAILED = "Saved Failed";
        public static final String UPDATED = "Updated Successfully";
        public static final String PROFILE_PIC_REMOVED = "Profile picture removed successfully";

        // Payment
        public static final String PAYMENT_ADDED = "Payment added successfully";
        public static final String PAYMENT_DETAILS_SUBMITTED = "Payment details submitted Successfully";
        public static final String PAYMENT_FAILED = "Something went wrong while adding payment";
        // Auth
        public static final String USER_NOT_AUTHENTICATED = "User not authenticated";
    }
}
