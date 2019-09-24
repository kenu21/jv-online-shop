package mate.academy.internetshop.model;

public class Role {
    private static long idGenerator = 0;
    private final Long id;
    private RoleName roleName;

    public Role() {
        id = idGenerator++;
    }

    public Role(RoleName roleName) {
        this();
        this.roleName = roleName;
    }

    public RoleName getRoleName() {
        return roleName;
    }

    public void setRoleName(RoleName roleName) {
        this.roleName = roleName;
    }

    public static Role of(String roleName) {
        return new Role(RoleName.valueOf(roleName));
    }

    public enum RoleName {
        USER, ADMIN;
    }
}
