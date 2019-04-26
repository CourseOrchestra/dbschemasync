package ru.curs.dbschemasync.ver;

/**
 * DBSchema version holder.
 *
 * @author Pavel Perminov (packpaul@mail.ru)
 * @since 2019-04-26
 */
public final class DBSchemaVersion {

    /**
     * DBSchema version, f.e. <code>1.0.1</code>.
     */
    public static final String VERSION;

    static {
        VERSION = DBSchemaVersion.class.getPackage().getSpecificationVersion();
    }

    private DBSchemaVersion() {
    }

}
