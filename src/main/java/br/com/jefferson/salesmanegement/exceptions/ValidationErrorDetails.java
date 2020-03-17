package br.com.jefferson.salesmanegement.exceptions;

public class ValidationErrorDetails extends ErrorDetails {

    private String field;
    private String fieldMessage;

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getFieldMessage() {
        return fieldMessage;
    }

    public void setFieldMessage(String fieldMessage) {
        this.fieldMessage = fieldMessage;
    }

    public static final class Builder {
        private String title;
        private Integer status;
        private String message;
        private String field;
        private String fieldMessage;
        private Long timestamp;

        private Builder() {
        }

        public static ValidationErrorDetails.Builder newBuilder() {
            return new ValidationErrorDetails.Builder();
        }

        public ValidationErrorDetails.Builder title(String title) {
            this.title = title;
            return this;
        }

        public ValidationErrorDetails.Builder status(Integer status) {
            this.status = status;
            return this;
        }

        public ValidationErrorDetails.Builder message(String message) {
            this.message = message;
            return this;
        }

        public ValidationErrorDetails.Builder field(String field) {
            this.field = field;
            return this;
        }

        public ValidationErrorDetails.Builder fieldMessage(String fieldMessage) {
            this.fieldMessage = fieldMessage;
            return this;
        }

        public ValidationErrorDetails.Builder timestamp(Long timestamp) {
            this.timestamp = timestamp;
            return this;
        }

        public ValidationErrorDetails build() {
            ValidationErrorDetails validationErrorDetails = new ValidationErrorDetails();
            validationErrorDetails.setTitle(title);
            validationErrorDetails.setStatus(status);
            validationErrorDetails.setMessage(message);
            validationErrorDetails.setTimestamp(timestamp);
            validationErrorDetails.setField(field);
            validationErrorDetails.setFieldMessage(fieldMessage);
            return validationErrorDetails;
        }
    }

}
