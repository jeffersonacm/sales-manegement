package br.com.jefferson.salesmanegement.exceptions;

public class ErrorDetails {

    private String title;
    private Integer status;
    private Long timestamp;
    private String message;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


    public static final class ErrorDetailsBuilder {
        private String title;
        private Integer status;
        private Long timestamp;
        private String message;

        private ErrorDetailsBuilder() {
        }

        public static ErrorDetailsBuilder newBuilder() {
            return new ErrorDetailsBuilder();
        }

        public ErrorDetailsBuilder title(String title) {
            this.title = title;
            return this;
        }

        public ErrorDetailsBuilder status(Integer status) {
            this.status = status;
            return this;
        }

        public ErrorDetailsBuilder timestamp(Long timestamp) {
            this.timestamp = timestamp;
            return this;
        }

        public ErrorDetailsBuilder message(String message) {
            this.message = message;
            return this;
        }

        public ErrorDetails build() {
            ErrorDetails errorDetails = new ErrorDetails();
            errorDetails.setTitle(title);
            errorDetails.setStatus(status);
            errorDetails.setTimestamp(timestamp);
            errorDetails.setMessage(message);
            return errorDetails;
        }
    }
}
