<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
    <xsl:output method="html" indent="yes"/>

    <!-- Определение параметров для фильтрации -->
    <xsl:param name="filterRoomID" select="''"/>
    <xsl:param name="filterStartDate" select="''"/>

    <xsl:template match="/">
        <html>
            <head>
                <title>Booking List</title>
                <style>
                    body { font-family: Arial, sans-serif; }
                    table { border-collapse: collapse; width: 100%; }
                    th, td { border: 1px solid #ddd; padding: 8px; text-align: center; }
                    th { background-color: #f2f2f2; }
                </style>
            </head>
            <body>
                <h1>Booking List</h1>
                <!-- Форма для фильтрации -->
                <form method="get">
                    <label for="roomID">Filter by Room ID:</label>
                    <input type="text" id="roomID" name="filterRoomID" value="{$filterRoomID}"/>
                    <br/>
                    <label for="startDate">Filter by Start Date:</label>
                    <input type="date" id="startDate" name="filterStartDate" value="{$filterStartDate}"/>
                    <br/>
                    <button type="submit">Apply Filters</button>
                </form>

                <!-- Таблица с данными -->
                <table>
                    <tr>
                        <th>ID</th>
                        <th>Room</th>
                        <th>RoomID</th>
                        <th>Start Date</th>
                        <th>End Date</th>
                    </tr>
                    <!-- Фильтрация записей -->
                    <xsl:for-each select="bookings/booking[
                        (roomID = $filterRoomID or $filterRoomID = '') and
                        (starts-with(startDate, $filterStartDate) or $filterStartDate = '')
                    ]">
                        <tr>
                            <td><xsl:value-of select="id"/></td>
                            <td><xsl:value-of select="room"/></td>
                            <td><xsl:value-of select="roomID"/></td>
                            <td><xsl:value-of select="startDate"/></td>
                            <td><xsl:value-of select="endDate"/></td>
                        </tr>
                    </xsl:for-each>
                </table>
            </body>
        </html>
    </xsl:template>
</xsl:stylesheet>
