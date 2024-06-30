import React from 'react'
import { useParams } from 'react-router-dom';

export default function DynamicPage() {
    const { '*': dynamicPath } = useParams();

    window.location.href = 'https://www.youtube.com/watch?v=Ehy3OftSHLs';

    return (
        <div>DynamicPage {dynamicPath}</div>
    )
}
