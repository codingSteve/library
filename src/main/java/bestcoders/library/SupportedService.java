package bestcoders.library;

/**
 * Allowing for extensibility of services. We can add more specific services to
 * support fees or bookings as the needs of the library change
 *
 * @author stevenpowell
 *
 */
public enum SupportedService {
    // Inventory services
    BORROW, RETURN,
    // Reporting and search services.
    AVAILABLE_ITEMS, OVERDUE_ITEMS
}
