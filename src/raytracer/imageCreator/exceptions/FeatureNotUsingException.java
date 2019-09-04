/**
 * 
 */
package raytracer.imageCreator.exceptions;

/**
 * @author Tobias Fox
 *
 */
public class FeatureNotUsingException extends Exception
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	public FeatureNotUsingException()
	{
	}

	/**
	 * @param message
	 */
	public FeatureNotUsingException( String message )
	{
		super(message);
	}

	/**
	 * @param cause
	 */
	public FeatureNotUsingException( Throwable cause )
	{
		super(cause);
	}

	/**
	 * @param message
	 * @param cause
	 */
	public FeatureNotUsingException( String message, Throwable cause )
	{
		super(message, cause);
	}

	/**
	 * @param message
	 * @param cause
	 * @param enableSuppression
	 * @param writableStackTrace
	 */
	public FeatureNotUsingException( String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace )
	{
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
