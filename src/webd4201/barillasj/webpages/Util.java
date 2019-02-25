package webd4201.barillasj.webpages;

import javax.servlet.http.HttpServletRequest;

/**
 * Contains various utility methods for use within the jsp files.
 * TODO: A bunch of string munging or create a class to hold the element type,
 *     attributes, content and children and generate the html from that?
 *
 * @author Jaime Barillas
 * @version 0.1.0
 * @since 2019-02-22
 */
public class Util {
    
    /**
     * Get the specified parameter from an HttpServletRequest or a default value.
     *
     * @param req The request that contains the parameter.
     * @param key The name of the parameter.
     * @param defaultValue The value to return if the parameter is null.
     * @return (String) The requested parameter.
     */
    public static String getReqParamOrDefault(HttpServletRequest req,
            String key, String defaultValue) {
        // Grab the parameter from the HttpServletRequest.
        String parameter = req.getParameter(key);
        
        // If it is null return the default value, otherwise return the parameter.
        return (parameter == null) ? defaultValue : parameter;
    }
    
    /**
     * Get the specified parameter from an HttpServletRequest or a default value.
     *
     * @param req The request that contains the parameter.
     * @param key The name of the parameter.
     * @param defaultValue The value to return if the parameter is null or invalid.
     * @return (long) The requested parameter.
     */
    public static long getReqParamOrDefault(HttpServletRequest req,
            String key, long defaultValue) {
        // Grab the parameter from the HttpServletRequest.
        String parameter = req.getParameter(key);
        long returnValue;
        
        // Check if it is a valid long. If it isn't, use the defaultValue.
        if (parameter == null) {
            returnValue = defaultValue;
        } else {
            try {
                returnValue = Long.parseLong(parameter);
            } catch (NumberFormatException ex) {
                returnValue = defaultValue;
            }
        }
        
        return returnValue;
    }
    
    /**
     * Generates the text content for an &lt;a&gt; tag based on a path.
     * The path should be in one of two formats:
     * <pre>
     * 1. <i>path</i>
     * 2. <i>path.jsp</i>
     * </pre>
     * In the case of 1, any hyphens ('-') will be replaced by spaces and the
     * remaining text will be Title Cased.
     * In the case of 2, the .jsp portion will be removed, then the same process
     * as above will occur.
     *
     * @param link (String) The url path to generate text for.
     * @return (String) The generated text.
     */
    private static String toLinkText(String link) {
        StringBuilder linkText = new StringBuilder();
        // Determines if we have to uppercase the next character.
        boolean shouldTitleCase = true;
        
        // First we need to trim the String and remove the file extension (if any).
        link = link.trim()
                   .replace(".jsp", "");
        
        // Now loop through each character and change it to UPPERCASE if it was
        // either the first character or came after a hyphen.
        for (char c : link.toCharArray()) {
            if (c == '-') {
                linkText.append(' ');
                shouldTitleCase = true;
            } else if (shouldTitleCase) {
                linkText.append(Character.toUpperCase(c));
                shouldTitleCase = false;
            } else {
                linkText.append(c);
            }
        }
        
        return linkText.toString();
    }
    
    /**
     * Generate the &lt;a&gt; tags for an array of links.
     * The links should be Strings in one of two formats:
     * <pre>
     * 1. <i>path</i>
     * 2. <i>path.jsp</i>
     * </pre>
     * In the case of 1, any hyphens ('-') will be replaced by spaces and the
     * remaining text will be Title Cased.
     * In the case of 2, the .jsp portion will be removed, then the same process
     * as above will occur.
     *
     * @see toLinkText
     * @param links (String[]) The links to generate the HTML for.
     * @return (String) The HTML containing the generated links.
     */
    public static String createNavBarLinks(String[] links) {
        // The general template for a navbar link.
        final String linkTemplate = "<a class=\"nav-link\" href=\"%s\">%s</a>";
        String navLinks = "";
        
        for (String link : links) {
            navLinks += String.format(linkTemplate, link, toLinkText(link));
        }
        
        return navLinks;
    }
}
