param(
    [string]$RemoteUser = "seulgae",
    [string]$RemoteHost = "192.168.219.105",
    [string]$Password = "rlfxogud1@"
)

Set-StrictMode -Version Latest
$ErrorActionPreference = "Stop"

Import-Module Posh-SSH

$secure = ConvertTo-SecureString $Password -AsPlainText -Force
$cred = New-Object System.Management.Automation.PSCredential($RemoteUser, $secure)

$session = New-SSHSession -ComputerName $RemoteHost -Credential $cred -AcceptKey

try {
    $command = @'
bash -lc '
set -e
printf "rlfxogud1@\n" | sudo -S dnf install -y podman podman-docker netavark aardvark-dns slirp4netns fuse-overlayfs
printf "rlfxogud1@\n" | sudo -S systemctl enable --now podman.socket || true
podman --version
free -h
'
'@

    Invoke-SSHCommand -SessionId $session.SessionId -Command $command -TimeOut 900 |
        Select-Object -ExpandProperty Output
}
finally {
    if ($session) {
        Remove-SSHSession -SessionId $session.SessionId | Out-Null
    }
}
