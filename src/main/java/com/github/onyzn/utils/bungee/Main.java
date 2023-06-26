package com.github.onyzn.utils.bungee;

import com.github.onyzn.utils.Manager;
import com.github.onyzn.utils.bungee.listeners.Listeners;
import tk.fancystore.noisier.bungee.plugin.NBungee;

public class Main extends NBungee {

  private static Main instance;

  @Override
  public void start() {}

  @Override
  public void load() {
    instance = this;

    Listeners.setupListeners();
    Manager.setupManager(this);

    this.getLogger().info("O plugin foi ativado.");
  }

  @Override
  public void enable() {

  }

  @Override
  public void disable() {
    this.getLogger().info("O plugin foi desativado.");
  }

  public static Main getInstance() {
    return instance;
  }
}
