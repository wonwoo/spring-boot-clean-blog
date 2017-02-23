package me.wonwoo.junit;

import org.junit.runner.Description;
import org.junit.runner.Runner;
import org.junit.runner.notification.RunNotifier;
import org.mockito.internal.runners.RunnerImpl;
import org.mockito.internal.runners.util.RunnerProvider;

/**
 * Created by wonwoo on 2017. 2. 12..
 */
public class MockitoJsonJUnitRunner extends Runner {

  private final RunnerImpl runner;

  public MockitoJsonJUnitRunner(Class<?> clazz) throws Exception {
    this.runner = new RunnerProvider().newInstance("me.wonwoo.junit.JsonRunner", clazz);
  }

  @Override
  public void run(final RunNotifier notifier) {
    runner.run(notifier);
  }

  @Override
  public Description getDescription() {
    return runner.getDescription();
  }
}