package csci318.analytics_service.model;

public class UserOrder {
    private Long userId;
    private Long activeOrderCount;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getActiveOrderCount() {
        return activeOrderCount;
    }

    public void setActiveOrderCount(Long activeOrderCount) {
        this.activeOrderCount = activeOrderCount;
    }
}
