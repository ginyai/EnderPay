package com.kamildanak.minecraft.enderpay.events;

import com.kamildanak.minecraft.enderpay.economy.Account;
import com.kamildanak.minecraft.enderpay.network.PacketDispatcher;
import com.kamildanak.minecraft.enderpay.network.client.MessageBalance;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.common.FMLCommonHandler;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.economy.EconomyTransactionEvent;
import org.spongepowered.api.service.economy.account.UniqueAccount;

import java.util.UUID;

public class SpongeHandler{
    @Listener
    public void onTransaction(EconomyTransactionEvent event) {
        if(event.getTransactionResult().getAccount() instanceof UniqueAccount) {
            UUID uuid = ((UniqueAccount)event.getTransactionResult().getAccount()).getUniqueId();
            EntityPlayerMP player = FMLCommonHandler.instance().getMinecraftServerInstance()
                    .getPlayerList().getPlayerByUUID(uuid);
            if(player==null){
                return;
            }
            Account account = Account.get(uuid);
            if(account==null){
                return;
            }
            PacketDispatcher.sendTo(new MessageBalance(account.getBalance()), player);
        }
    }

}
