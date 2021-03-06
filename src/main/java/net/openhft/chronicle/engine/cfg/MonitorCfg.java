/*
 *     Copyright (C) 2015  higherfrequencytrading.com
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU Lesser General Public License as published by
 *     the Free Software Foundation, either version 3 of the License.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU Lesser General Public License for more details.
 *
 *     You should have received a copy of the GNU Lesser General Public License
 *     along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package net.openhft.chronicle.engine.cfg;

import net.openhft.chronicle.engine.api.tree.AssetTree;
import net.openhft.chronicle.engine.tree.VanillaAsset;
import net.openhft.chronicle.wire.AbstractMarshallable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URISyntaxException;

/**
 * Created by peter on 26/08/15.
 */
public class MonitorCfg extends AbstractMarshallable implements Installable {
    private static final Logger LOGGER = LoggerFactory.getLogger(MonitorCfg.class);
    private boolean subscriptionMonitoringEnabled;
    private boolean userMonitoringEnabled;

    @Override
    public MonitorCfg install(String path, AssetTree assetTree) throws IOException, URISyntaxException {
        ((VanillaAsset) assetTree.acquireAsset("/proc")).configMapServer();
        if (subscriptionMonitoringEnabled) {
            LOGGER.info("Enabling Subscription Monitoring for " + assetTree);
            assetTree.acquireMap("/proc/subscriptions", String.class, SubscriptionStat.class);
        }
        if (userMonitoringEnabled) {
            LOGGER.info("Enabling User Monitoring for " + assetTree);
            assetTree.acquireMap("/proc/users", String.class, UserStat.class);
        }
        return this;
    }
}
