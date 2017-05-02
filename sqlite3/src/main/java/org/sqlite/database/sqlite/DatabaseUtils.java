package org.sqlite.database.sqlite;

import java.util.Locale;

/**
 * Static utility methods for dealing with databases; cloned and trimed from Android source.
 *
 * Created by pjw on 20-Apr-2017.
 */
@SuppressWarnings("WeakerAccess")
public class DatabaseUtils {
	/** One of the values returned by {@link #getSqlStatementType(String)}. */
	public static final int STATEMENT_SELECT = 1;
	/** One of the values returned by {@link #getSqlStatementType(String)}. */
	public static final int STATEMENT_UPDATE = 2;
	/** One of the values returned by {@link #getSqlStatementType(String)}. */
	public static final int STATEMENT_ATTACH = 3;
	/** One of the values returned by {@link #getSqlStatementType(String)}. */
	public static final int STATEMENT_BEGIN = 4;
	/** One of the values returned by {@link #getSqlStatementType(String)}. */
	public static final int STATEMENT_COMMIT = 5;
	/** One of the values returned by {@link #getSqlStatementType(String)}. */
	public static final int STATEMENT_ABORT = 6;
	/** One of the values returned by {@link #getSqlStatementType(String)}. */
	public static final int STATEMENT_PRAGMA = 7;
	/** One of the values returned by {@link #getSqlStatementType(String)}. */
	public static final int STATEMENT_DDL = 8;
	/** One of the values returned by {@link #getSqlStatementType(String)}. */
	public static final int STATEMENT_UNPREPARED = 9;
	/** One of the values returned by {@link #getSqlStatementType(String)}. */
	public static final int STATEMENT_OTHER = 99;

	/**
	 * Appends an SQL string to the given StringBuilder, including the opening
	 * and closing single quotes. Any single quotes internal to sqlString will
	 * be escaped.
	 *
	 * This method is deprecated because we want to encourage everyone
	 * to use the "?" binding form.  However, when implementing a
	 * ContentProvider, one may want to add WHERE clauses that were
	 * not provided by the caller.  Since "?" is a positional form,
	 * using it in this case could break the caller because the
	 * indexes would be shifted to accomodate the ContentProvider's
	 * internal bindings.  In that case, it may be necessary to
	 * construct a WHERE clause manually.  This method is useful for
	 * those cases.
	 *
	 * @param sb the StringBuilder that the SQL string will be appended to
	 * @param sqlString the raw string to be appended, which may contain single
	 *                  quotes
	 */
	public static void appendEscapedSQLString(StringBuilder sb, String sqlString) {
		sb.append('\'');
		if (sqlString.indexOf('\'') != -1) {
			int length = sqlString.length();
			for (int i = 0; i < length; i++) {
				char c = sqlString.charAt(i);
				if (c == '\'') {
					sb.append('\'');
				}
				sb.append(c);
			}
		} else
			sb.append(sqlString);
		sb.append('\'');
	}

	public static int getSqlStatementType(String sql) {
		sql = sql.trim();
		if (sql.length() < 3) {
			return STATEMENT_OTHER;
		}
		String prefixSql = sql.substring(0, 3).toUpperCase(Locale.ROOT);
		switch (prefixSql) {
			case "SEL":
				return STATEMENT_SELECT;
			case "INS":
			case "UPD":
			case "REP":
			case "DEL":
				return STATEMENT_UPDATE;
			case "ATT":
				return STATEMENT_ATTACH;
			case "COM":
				return STATEMENT_COMMIT;
			case "END":
				return STATEMENT_COMMIT;
			case "ROL":
				return STATEMENT_ABORT;
			case "BEG":
				return STATEMENT_BEGIN;
			case "PRA":
				return STATEMENT_PRAGMA;
			case "CRE":
			case "DRO":
			case "ALT":
				return STATEMENT_DDL;
			case "ANA":
			case "DET":
				return STATEMENT_UNPREPARED;
		}
		return STATEMENT_OTHER;
	}
}
