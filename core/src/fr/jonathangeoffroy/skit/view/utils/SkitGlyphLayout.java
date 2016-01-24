package fr.jonathangeoffroy.skit.view.utils;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.utils.Array;

/**
 * A GlyphLayout which is able to return the first line displayed
 * @author Jonathan Geoffroy
 */
public class SkitGlyphLayout extends GlyphLayout {
    public SkitGlyphLayout() {
        super();
    }

    /**
     * @return the first line
     */
    public String firstLine() {
        Array<BitmapFont.Glyph> glyphs = this.runs.first().glyphs;
        StringBuilder buffer = new StringBuilder(glyphs.size);

        for (int i = 0, n = glyphs.size; i < n; i++) {
            BitmapFont.Glyph g = glyphs.get(i);
            buffer.append((char) g.id);
        }

        return buffer.toString();
    }
}
