package com.atherys.towns.commands;

import com.atherys.towns.AtherysTowns;
import org.spongepowered.api.command.CommandCallable;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.action.TextActions;
import org.spongepowered.api.text.format.TextColor;
import org.spongepowered.api.text.format.TextStyles;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public abstract class TownsMasterCommand extends TownsSimpleCommand {

    protected Map<List<String>, CommandCallable> children = new LinkedHashMap<>();

    public Map<List<String>, CommandCallable> getChildren () {
        return children;
    }

    protected void addChild ( CommandCallable callable, String... aliases ) {
        children.put( Arrays.asList( aliases ), callable );
    }

    public void showHelp ( String cmd, Player player ) {

        TextColor decoration = AtherysTowns.getConfig().COLORS.DECORATION;
        TextColor primary = AtherysTowns.getConfig().COLORS.PRIMARY;

        player.sendMessage( Text.of( decoration, ".o0o.=---------= { ", TextStyles.BOLD, primary, "/", cmd, " Help", TextStyles.RESET, decoration, " } =---------=.o0o." ) );
        for ( Map.Entry<List<String>, CommandCallable> entry : getChildren().entrySet() ) {
            Text cmdHelp = Text.of( TextStyles.BOLD, primary, "/", cmd, " ", entry.getKey().get( 0 ), " ", entry.getValue().getUsage( player ) );
            Text helpMsg = Text.builder()
                    .append( cmdHelp )
                    .onHover( TextActions.showText(
                            entry.getValue().getHelp( player ).orElse( Text.of( "Help Unavailable" ) )
                    ) )
                    .onClick(
                            TextActions.suggestCommand( cmdHelp.toPlain() )
                    )
                    .build();
            player.sendMessage( helpMsg );
        }
        player.sendMessage( Text.of( decoration, ".o0o.=---------= { ", TextStyles.BOLD, primary, "/", cmd, " Help", TextStyles.RESET, decoration, " } =---------=.o0o." ) );
    }

}