/*
 * Copyright 2009 The Closure Compiler Authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.javascript.jscomp;

import com.google.common.collect.Lists;
import com.google.javascript.jscomp.CompilerOptions.TracerMode;
import com.google.javascript.jscomp.PhaseOptimizer.Loop;
import com.google.javascript.rhino.Node;
import com.google.javascript.rhino.Token;

import junit.framework.TestCase;

import java.util.List;
import java.util.Random;

/**
 * Tests for {@link PhaseOptimizer}.
 * @author nicksantos@google.com (Nick Santos)
 */
public class PhaseOptimizerTest extends TestCase {
  private final List<String> passesRun = Lists.newArrayList();
<<<<<<< HEAD
=======
  private final Node dummyRoot = new Node(Token.BLOCK);
>>>>>>> 5c522db6e745151faa1d8dc310d145e94f78ac77
  private PhaseOptimizer optimizer;
  private Compiler compiler;
  private PerformanceTracker tracker;

  @Override
  public void setUp() {
    passesRun.clear();
    compiler = new Compiler();
    compiler.initCompilerOptionsIfTesting();
<<<<<<< HEAD
    tracker = new PerformanceTracker(
        new Node(Token.BLOCK), TracerMode.TIMING_ONLY);
=======
    tracker = new PerformanceTracker(dummyRoot, TracerMode.TIMING_ONLY);
>>>>>>> 5c522db6e745151faa1d8dc310d145e94f78ac77
    optimizer = new PhaseOptimizer(compiler, tracker, null);
  }

  public void testOneRun() {
    addOneTimePass("x");
    assertPasses("x");
  }

  public void testLoop1() {
    Loop loop = optimizer.addFixedPointLoop();
    addLoopedPass(loop, "x", 0);
    assertPasses("x");
  }

  public void testLoop2() {
    Loop loop = optimizer.addFixedPointLoop();
    addLoopedPass(loop, "x", 3);
    assertPasses("x", "x", "x", "x");
  }

  public void testLoop3() {
    Loop loop = optimizer.addFixedPointLoop();
    addLoopedPass(loop, "x", 3);
    addLoopedPass(loop, "y", 1);
    assertPasses("x", "y", "x", "y", "x", "x", "y");
  }

  public void testNotInfiniteLoop() {
    Loop loop = optimizer.addFixedPointLoop();
    addLoopedPass(loop, "x", PhaseOptimizer.MAX_LOOPS - 1);
<<<<<<< HEAD
    optimizer.process(null, null);
=======
    optimizer.process(null, dummyRoot);
>>>>>>> 5c522db6e745151faa1d8dc310d145e94f78ac77
    assertEquals("There should be no errors.", 0, compiler.getErrorCount());
  }

  public void testInfiniteLoop() {
    Loop loop = optimizer.addFixedPointLoop();
    addLoopedPass(loop, "x", PhaseOptimizer.MAX_LOOPS + 1);
    try {
<<<<<<< HEAD
      optimizer.process(null, null);
=======
      optimizer.process(null, dummyRoot);
>>>>>>> 5c522db6e745151faa1d8dc310d145e94f78ac77
      fail("Expected RuntimeException");
    } catch (RuntimeException e) {
      assertTrue(e.getMessage().contains(PhaseOptimizer.OPTIMIZE_LOOP_ERROR));
    }
  }

  public void testCombined() {
    addOneTimePass("a");
    Loop loop = optimizer.addFixedPointLoop();
    addLoopedPass(loop, "x", 3);
    addLoopedPass(loop, "y", 1);
    addOneTimePass("z");
    assertPasses("a", "x", "y", "x", "y", "x", "x", "y", "z");
  }

<<<<<<< HEAD
  public void testSanityCheck() {
    Loop loop = optimizer.addFixedPointLoop();
    addLoopedPass(loop, "x", 1);
    addOneTimePass("z");
    optimizer.setSanityCheck(
        createPassFactory("sanity", createPass("sanity", 0), false));
    assertPasses("x", "sanity", "x", "sanity", "z", "sanity");
  }

=======
>>>>>>> 5c522db6e745151faa1d8dc310d145e94f78ac77
  public void testConsumption1() {
    optimizer.consume(
        Lists.newArrayList(
            createPassFactory("a", 0, true),
            createPassFactory("b", 1, false),
            createPassFactory("c", 2, false),
            createPassFactory("d", 1, false),
            createPassFactory("e", 1, true),
            createPassFactory("f", 0, true)));
    assertPasses("a", "b", "c", "d", "b", "c", "d", "c", "b", "d", "e", "f");
  }

  public void testConsumption2() {
    optimizer.consume(
        Lists.newArrayList(
            createPassFactory("a", 2, false),
            createPassFactory("b", 1, true),
            createPassFactory("c", 1, false)));
    assertPasses("a", "a", "a", "b", "c", "c");
  }

  public void testConsumption3() {
    optimizer.consume(
        Lists.newArrayList(
            createPassFactory("a", 2, true),
            createPassFactory("b", 0, false),
            createPassFactory("c", 0, false)));
    assertPasses("a", "b", "c");
  }

  public void testDuplicateLoop() {
    Loop loop = optimizer.addFixedPointLoop();
    addLoopedPass(loop, "x", 1);
    try {
      addLoopedPass(loop, "x", 1);
      fail("Expected exception");
<<<<<<< HEAD
    } catch (IllegalArgumentException e) {}
=======
    } catch (IllegalArgumentException e) {
      return;
    }
>>>>>>> 5c522db6e745151faa1d8dc310d145e94f78ac77
  }

  public void testPassOrdering() {
    Loop loop = optimizer.addFixedPointLoop();
    List<String> optimalOrder = Lists.newArrayList(
        PhaseOptimizer.OPTIMAL_ORDER);
    Random random = new Random();
    while (optimalOrder.size() > 0) {
      addLoopedPass(
          loop, optimalOrder.remove(random.nextInt(optimalOrder.size())), 0);
    }
<<<<<<< HEAD
    optimizer.process(null, null);
=======
    optimizer.process(null, dummyRoot);
>>>>>>> 5c522db6e745151faa1d8dc310d145e94f78ac77
    assertEquals(PhaseOptimizer.OPTIMAL_ORDER, passesRun);
  }

  public void testProgress() {
    final List<Double> progressList = Lists.newArrayList();
    compiler = new Compiler() {
      @Override void setProgress(double p, String name) {
        progressList.add(p);
      }
    };
    compiler.initCompilerOptionsIfTesting();
    optimizer = new PhaseOptimizer(compiler, null,
        new PhaseOptimizer.ProgressRange(0, 100));
    addOneTimePass("x1");
    addOneTimePass("x2");
    addOneTimePass("x3");
    addOneTimePass("x4");
<<<<<<< HEAD
    optimizer.process(null, null);
=======
    optimizer.process(null, dummyRoot);
>>>>>>> 5c522db6e745151faa1d8dc310d145e94f78ac77
    assertEquals(4, progressList.size());
    assertEquals(25, Math.round(progressList.get(0)));
    assertEquals(50, Math.round(progressList.get(1)));
    assertEquals(75, Math.round(progressList.get(2)));
    assertEquals(100, Math.round(progressList.get(3)));
  }

  public void assertPasses(String ... names) {
<<<<<<< HEAD
    optimizer.process(null, null);
=======
    optimizer.process(null, dummyRoot);
>>>>>>> 5c522db6e745151faa1d8dc310d145e94f78ac77
    assertEquals(Lists.newArrayList(names), passesRun);
  }

  private void addOneTimePass(String name) {
    optimizer.addOneTimePass(
        createPassFactory(name, 0, true));
  }

  private void addLoopedPass(Loop loop, String name, int numChanges) {
    loop.addLoopedPass(
        createPassFactory(name, numChanges, false));
  }

  private PassFactory createPassFactory(
      String name, int numChanges, boolean isOneTime) {
    return createPassFactory(name, createPass(name, numChanges), isOneTime);
  }

  private PassFactory createPassFactory(
      String name, final CompilerPass pass, boolean isOneTime) {
    return new PassFactory(name, isOneTime) {
      @Override
      protected CompilerPass create(AbstractCompiler compiler) {
        return pass;
      }
    };
  }

  private CompilerPass createPass(final String name, int numChanges) {
    final int[] numChangesClosure = new int[] {numChanges};
    return new CompilerPass() {
      @Override public void process(Node externs, Node root) {
        passesRun.add(name);
        if (numChangesClosure[0] > 0) {
          compiler.reportCodeChange();
          numChangesClosure[0] = numChangesClosure[0] - 1;
        }
      }
    };
  }
}
