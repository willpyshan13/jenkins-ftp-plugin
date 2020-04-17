/*
 * The MIT License
 *
 * Copyright (C) 2010-2011 by Anthony Robinson
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package jenkins.plugins.publish_over_ftp.options;

import hudson.Extension;
import hudson.model.Describable;
import hudson.model.Descriptor;
import jenkins.model.Jenkins;
import jenkins.plugins.publish_over.options.PublisherOptions;
import jenkins.plugins.publish_over_ftp.BapFtpPublisherPlugin;
import org.kohsuke.stapler.DataBoundConstructor;

public class FtpOverridePublisherDefaults implements PublisherOptions, Describable<FtpOverridePublisherDefaults> {

    private final String configName;
    private final boolean useWorkspaceInPromotion;
    private final boolean usePromotionTimestamp;
    private final boolean verbose;

    @DataBoundConstructor
    public FtpOverridePublisherDefaults(final String configName, final boolean useWorkspaceInPromotion, final boolean usePromotionTimestamp,
                                        final boolean verbose) {
        this.configName = configName;
        this.usePromotionTimestamp = usePromotionTimestamp;
        this.useWorkspaceInPromotion = useWorkspaceInPromotion;
        this.verbose = verbose;
    }

    public String getConfigName() {
        return configName;
    }

    public boolean isUseWorkspaceInPromotion() {
        return useWorkspaceInPromotion;
    }

    public boolean isUsePromotionTimestamp() {
        return usePromotionTimestamp;
    }

    public boolean isVerbose() {
        return verbose;
    }

    public FtpOverridePublisherDefaultsDescriptor getDescriptor() {
        return Jenkins.getInstance().getDescriptorByType(FtpOverridePublisherDefaultsDescriptor.class);
    }

    @Extension
    public static class FtpOverridePublisherDefaultsDescriptor extends Descriptor<FtpOverridePublisherDefaults> {

        @Override
        public String getDisplayName() {
            return "FtpOverridePublisherDefaultsDescriptor - not visible ...";
        }

        public BapFtpPublisherPlugin.Descriptor getPublisherPluginDescriptor() {
            return Jenkins.getInstance().getDescriptorByType(BapFtpPublisherPlugin.Descriptor.class);
        }

        public jenkins.plugins.publish_over.view_defaults.BapPublisher.Messages getCommonFieldNames() {
            return new jenkins.plugins.publish_over.view_defaults.BapPublisher.Messages();
        }

    }

}
