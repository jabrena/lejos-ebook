/*
 * Copyright (C) 2011 Google Inc.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package org.ros.tutorials.pubsub;

import org.apache.commons.logging.Log;
import org.ros.message.MessageListener;
import org.ros.node.Node;
import org.ros.node.NodeMain;
import org.ros.node.topic.Subscriber;

/**
 * This is a simple rosjava {@link Subscriber} {@link Node}. It assumes an
 * external roscore is already running.
 * 
 * @author damonkohler@google.com (Damon Kohler)
 */
public class Listener implements NodeMain {

  @Override
  public void onStart(Node node) {
    final Log log = node.getLog();
    Subscriber<org.ros.message.std_msgs.String> subscriber =
        node.newSubscriber("chatter", "std_msgs/String");
    subscriber.addMessageListener(new MessageListener<org.ros.message.std_msgs.String>() {
      @Override
      public void onNewMessage(org.ros.message.std_msgs.String message) {
        log.info("I heard: \"" + message.data + "\"");
      }
    });
  }

  @Override
  public void onShutdown(Node node) {
  }

  @Override
  public void onShutdownComplete(Node node) {
  }
}
