/**
Author: Harish Jangam
create a SSH shared Agent from groovy
**/

import com.cloudbees.opscenter.server.jnlp.slave.JocJnlpSlaveLauncher
import com.cloudbees.opscenter.server.model.SharedNodeRetentionStrategy
import com.cloudbees.opscenter.server.model.SharedSlave
import com.cloudbees.opscenter.server.properties.EnvironmentVariablesNodePropertyCustomizer
import com.cloudbees.opscenter.server.properties.NodePropertyCustomizer
import com.cloudbees.opscenter.server.properties.SharedSlaveNodePropertyCustomizer
import hudson.model.Node
import hudson.slaves.EnvironmentVariablesNodeProperty
import hudson.slaves.*
import hudson.plugins.sshslaves.*
import com.cloudbees.jenkins.plugins.sshslaves.verification.*
import com.cloudbees.jenkins.plugins.sshslaves.SSHConnectionDetails

import hudson.tools.ToolLocationNodeProperty
import hudson.tools.ToolProperty
import com.cloudbees.opscenter.server.model.*

SharedSlave instance = jenkins.model.Jenkins.getInstance().createProject(SharedSlave.class,"ShareAgent11Name")

ServerKeyVerificationStrategy serverKeyVerificationStrategy = new TrustInitialConnectionVerificationStrategy(false) // "Manually trusted key verification Strategy"


instance.setLauncher(new com.cloudbees.jenkins.plugins.sshslaves.SSHLauncher(
        "dc1t1962ouijh", // Host
        new SSHConnectionDetails(
                "avsrelmgmttest", // Credentials ID
                22, // port
                "/app/jdk1.8.0_111/bin/java", // JavaPath
                (String)null, // JVM Options
                (String)null, // Prefix Start Slave Command
                (String)null, // Suffix Start Slave Command
                (boolean)false, // Log environment on initial connect
                (ServerKeyVerificationStrategy) serverKeyVerificationStrategy // Host Key Verification Strategy
        )
)
)


instance.setNumExecutors(4)
instance.setLabelString("builds")
instance.setMode(Node.Mode.NORMAL)
instance.setRemoteFS("/app/jenkinsagentshared")

instance.setRetentionStrategy(new RetentionStrategy.Always())
instance.save()